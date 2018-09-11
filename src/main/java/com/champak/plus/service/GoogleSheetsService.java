package com.champak.plus.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.champak.plus.util.GoogleBuilderUtil;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

///https://github.com/eugenp/tutorials/blob/master/libraries/src/test/java/com/baeldung/google/sheets/GoogleSheetsIntegrationTest.java

@Service
public class GoogleSheetsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${scuapi.google.sheet.spreadsheet_id}")
    static String SPREADSHEET_ID;

    private static Sheets sheetsService;

    @Autowired
    GooglePermissionService googlePermissionService;

    private static Drive driveService;

    // this id can be replaced with your spreadsheet id
    // otherwise be advised that multiple people may run this test and update the public spreadsheet
    //private static final String SPREADSHEET_ID = "1sILuxZUnyl_7-MlNThjt765oWshN3Xs-PPLfqYe4DhI";



    public static void setup() throws GeneralSecurityException, IOException {
        sheetsService = GoogleBuilderUtil.getSheetsService();
        driveService = GoogleBuilderUtil.getDriveService();
    }


    public AppendValuesResponse whenWriteSheet_thenReadSheetOk(String SPREADSHEET_ID, List rowData) throws IOException, GeneralSecurityException {
        setup();
        logger.info("inserting record");
        ValueRange appendBody = new ValueRange().setValues(Arrays.asList(rowData));
        AppendValuesResponse appendResult = sheetsService.spreadsheets().values().append(SPREADSHEET_ID, "A1", appendBody).setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true).execute();
        logger.info("inserting record - done");
        logger.info("setting perm");
        googlePermissionService.insertPermission(driveService, appendResult.getSpreadsheetId());
        logger.info("setting perm - done");
        return appendResult;
    }

    private UpdateValuesResponse whenWriteSheet_thenReadSheetOk(String SPREADSHEET_ID) throws IOException, GeneralSecurityException {
        setup();
        ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList("Expenses January"), Arrays.asList("books", "30"), Arrays.asList("pens", "10"), Arrays.asList("Expenses February"), Arrays.asList("clothes", "20"), Arrays.asList("shoes", "5")));
        UpdateValuesResponse result = sheetsService.spreadsheets().values().update(SPREADSHEET_ID, "A1", body).setValueInputOption("RAW").execute();

        List<ValueRange> data = new ArrayList<>();
        data.add(new ValueRange().setRange("D1").setValues(Arrays.asList(Arrays.asList("January Total", "=B2+B3"))));
        data.add(new ValueRange().setRange("D4").setValues(Arrays.asList(Arrays.asList("February Total", "=B5+B6"))));

        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest().setValueInputOption("USER_ENTERED").setData(data);
        BatchUpdateValuesResponse batchResult = sheetsService.spreadsheets().values().batchUpdate(SPREADSHEET_ID, batchBody).execute();

        List<String> ranges = Arrays.asList("E1", "E4");
        BatchGetValuesResponse readResult = sheetsService.spreadsheets().values().batchGet(SPREADSHEET_ID).setRanges(ranges).execute();

        ValueRange januaryTotal = readResult.getValueRanges().get(0);

        ValueRange febTotal = readResult.getValueRanges().get(1);

        ValueRange appendBody = new ValueRange().setValues(Arrays.asList(Arrays.asList("Total", "=E1+E4")));
        AppendValuesResponse appendResult = sheetsService.spreadsheets().values().append(SPREADSHEET_ID, "A1", appendBody).setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true).execute();

        ValueRange total = appendResult.getUpdates().getUpdatedData();

        return result;
    }

    public BatchUpdateSpreadsheetResponse whenUpdateSpreadSheetTitle_thenOk(String SPREADSHEET_ID, String title) throws IOException, GeneralSecurityException {
        setup();
        UpdateSpreadsheetPropertiesRequest updateRequest = new UpdateSpreadsheetPropertiesRequest().setFields("*").setProperties(new SpreadsheetProperties().setTitle(title));

        CopyPasteRequest copyRequest = new CopyPasteRequest().setSource(new GridRange().setSheetId(0).setStartColumnIndex(0).setEndColumnIndex(2).setStartRowIndex(0).setEndRowIndex(1))
                .setDestination(new GridRange().setSheetId(1).setStartColumnIndex(0).setEndColumnIndex(2).setStartRowIndex(0).setEndRowIndex(1)).setPasteType("PASTE_VALUES");

        List<Request> requests = new ArrayList<>();

        requests.add(new Request().setCopyPaste(copyRequest));
        requests.add(new Request().setUpdateSpreadsheetProperties(updateRequest));

        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);

        return sheetsService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();
    }

    public Spreadsheet whenCreateSpreadSheet_thenIdOk(String spreadSheetName) throws IOException, GeneralSecurityException {
        setup();
        Spreadsheet spreadSheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(spreadSheetName));
        Spreadsheet result = sheetsService.spreadsheets().create(spreadSheet).execute();
        return result;
    }

}
