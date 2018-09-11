package com.champak.plus.controller.champak;

import com.champak.plus.model.CustomResponse;
import com.champak.plus.model.champak.ContestForm;
import com.champak.plus.repository.champak.ContestFormRepository;
import com.champak.plus.service.GoogleDriveService;
import com.champak.plus.service.GoogleSheetsService;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/contest/")
public class ContestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${scuapi.response.success.code}")
    String success_code;

    @Value("${scuapi.response.error.code}")
    String error_code;

    @Value("${scuapi.response.warning.code}")
    String warning_code;

    @Value("${scuapi.contest.form.date.format}")
    String contest_form_date_format;

    @Autowired
    GoogleSheetsService googleSheetsService;

    @Autowired
    GoogleDriveService googleDriveService;

    @Autowired
    ContestFormRepository contestFormRepository;

    @GetMapping("headertag")
    public CustomResponse getHeaderTag() {
        logger.info("headertag");
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Top Deals & Discounts");
        return customResponse;
    }

    @PostMapping("submit")
    public CustomResponse submitContest(@RequestBody ContestForm contestForm){
        logger.info("submit request received");
        logger.info("submit request received contestForm:"+contestForm.toString());
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");
        return customResponse;
    }

    @PostMapping("create/sheet")
    public CustomResponse createSheet(@RequestParam("name") String sheetName){
        logger.info("createSheet request received");
        logger.info("createSheet request received sheetName:"+sheetName);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");
        try {
            Spreadsheet result = googleSheetsService.whenCreateSpreadSheet_thenIdOk(sheetName);
            customResponse.setData(result);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        }
        return customResponse;
    }

    @PostMapping("update/title")
    public CustomResponse updateTitle(@RequestParam("SPREADSHEET_ID") String SPREADSHEET_ID, @RequestParam("newTitle") String newTitle){
        logger.info("updateTitle request received");
        logger.info("updateTitle request received newTitle:"+newTitle);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");
        try {
            BatchUpdateSpreadsheetResponse result = googleSheetsService.whenUpdateSpreadSheetTitle_thenOk(SPREADSHEET_ID, newTitle);
            customResponse.setData(result);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        }
        return customResponse;
    }

    @PostMapping("upload/file")
    public CustomResponse uploadFile(@RequestBody ContestForm contestForm){
        logger.info("uploadFile request received");
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");
        try {
            com.google.api.services.drive.model.File result = googleDriveService.uploadFile(contestForm.getFileData().getFile(), "Test File", "test des");
            customResponse.setData(result);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        }
        return customResponse;
    }

    @PostMapping("get/file/{documentId}")
    public CustomResponse uploadFile(@PathVariable("documentId") String documentId){
        logger.info("getFile request received");
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");
        try {
            Map map = new HashMap();
            com.google.api.services.drive.model.File result = googleDriveService.getFileInfo(documentId);
            map.put("metaData",result);
            map.put("file",googleDriveService.getFile(documentId));
            customResponse.setData(map);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        }
        return customResponse;
    }

    @PostMapping("write")
    public CustomResponse write(@RequestBody ContestForm contestForm){
        //1mae8BNzd--FSddZpI5guKwmP8A35lal6QXeERBKalQA test id
                    logger.info("write request received");
        logger.info("write request received contestForm:"+contestForm.toString());
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");

        //set default values
        contestForm.setModifiedDate(new Date());
        if(contestForm.getCreatedDate() == null)
            contestForm.setCreatedDate(new Date());
        if(contestForm.getContestIssue() == null || contestForm.getContestIssue().isEmpty() || contestForm.getContestIssue().length() < 1)
            contestForm.setContestIssue(new SimpleDateFormat("MMMM").format(new Date()));

        try {
            List rowData = new ArrayList();
            rowData.add(new SimpleDateFormat(contest_form_date_format).format(contestForm.getCreatedDate()));
            rowData.add(new SimpleDateFormat(contest_form_date_format).format(contestForm.getModifiedDate()));
            rowData.add(contestForm.getContestIssue());
            rowData.add(contestForm.getSchoolName());
            rowData.add(contestForm.getCandidateClass());
            rowData.add(contestForm.getCandidateSection());
            rowData.add(contestForm.getCandidateName());
            rowData.add(contestForm.getCandidateRollNo());
            //rowData.add(contestForm.getFile());

            String fileName = contestForm.getContestIssue() +"_"+
                    new Date()+"_"+
                    contestForm.getCandidateClass()+"_"+
                    contestForm.getCandidateSection()+"_"+
                    contestForm.getCandidateName();

            fileName = fileName.replace(" ","_");

            String description = "Contest issue: " + contestForm.getContestIssue()+"\n";
            description += "School Name: " + contestForm.getSchoolName()+"\n";
            description += "Class: "+ contestForm.getSchoolName()+"\n";
            description += "Section: "+ contestForm.getCandidateSection()+"\n";
            description += "Student Name: "+ contestForm.getCandidateName()+"\n";
            description += "Student Roll No: "+ contestForm.getCandidateRollNo()+"\n";
            description += "File Id: "+ contestForm.getFileData().getId();

            com.google.api.services.drive.model.File fileResult = googleDriveService.uploadFile(contestForm.getFileData().getFile(), fileName, description);
            logger.info("fileResult:" + fileResult.toString());

            if(fileResult.getId() != null){
                rowData.add(fileResult.getId());
                if(contestForm.getSpreadsheetId() != null) {
                    AppendValuesResponse result = googleSheetsService.whenWriteSheet_thenReadSheetOk(contestForm.getSpreadsheetId(), rowData);
                    customResponse.setData(result);
                }
                else {
                    contestForm.getFileData().setFile(null);
                    contestForm.getFileData().setId(fileResult.getId());
                    contestForm.setId(fileResult.getId());
                    contestFormRepository.save(contestForm);
                    contestForm = contestFormRepository.findOne(fileResult.getId());
                    customResponse.setData(contestForm);
                }
            }else {
                logger.error("File uploading error");
            }




        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        }
        return customResponse;
    }

}
