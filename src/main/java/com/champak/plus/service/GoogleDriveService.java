package com.champak.plus.service;

import com.champak.plus.util.GoogleBuilderUtil;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.net.URLConnection;
import java.security.GeneralSecurityException;

@Service
public class GoogleDriveService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GooglePermissionService googlePermissionService;

    private static Drive driveService;

    public static void setup() throws GeneralSecurityException, IOException {
        driveService = GoogleBuilderUtil.getDriveService();
    }

    public File uploadFile(byte data[], String fileName, String description) throws IOException, GeneralSecurityException, Exception {
        setup();
        logger.info("uploading file");
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setDescription(description);

        String workingDir = System.getProperty("user.dir");


        String tempFile = workingDir+"/tempFile.";
        tempFile += getFileExtension(data);
        Files.write(new java.io.File(tempFile).toPath(), data);
        String mimeType = getMimeType(data);

        java.io.File filePath = new java.io.File(tempFile);
        FileContent mediaContent = new FileContent(mimeType, filePath);
        File file = driveService.files().create(fileMetadata, mediaContent).execute();
        //System.out.println("File ID: " + file.getId());
        logger.info("uploading file - done");
        logger.info("setting perm");
        googlePermissionService.insertPermission(driveService, file.getId());
        logger.info("setting perm - done");
        return file;
    }


    public File getFileInfo(String fileId) throws IOException, GeneralSecurityException {
        setup();
        File file = driveService.files().get(fileId).execute();
        return file;
    }

    public byte[] getFile(String fileId) throws IOException, GeneralSecurityException {
        setup();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        driveService.files().get(fileId).executeMediaAndDownloadTo(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    private String getMimeType(byte data[]) throws Exception {
        return URLConnection.guessContentTypeFromStream(new BufferedInputStream(new ByteArrayInputStream(data)));
    }

    private String getFileExtension(byte data[]) throws Exception {
        String exe =  URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));
        if(exe.split("/").length > 0)
            exe = exe.split("/")[1];
        return exe.toLowerCase();
    }
}
