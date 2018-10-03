package com.champak.plus.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.champak.plus.util.AmazonBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLConnection;
import java.nio.file.Files;

@Service
public class AmazonS3Service {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    String bucketName = "virusrekognition";

    public PutObjectResult uploadFile(byte data[], String fileName) throws Exception {
        logger.info("uploading file to s3");

        String workingDir = System.getProperty("user.dir");
        String tempFile = workingDir+fileName;
        Files.write(new java.io.File(tempFile).toPath(), data);

        PutObjectResult PutObjectResult = uploadFile(fileName, tempFile);


        if(new File(tempFile).delete())
            logger.info(tempFile+" File deleted successfully");
        else
            logger.warn(tempFile+" Failed to delete the file");

        return PutObjectResult;
    }

    private PutObjectResult uploadFile(String fileName, String filePath) {

        AmazonS3 s3Client = AmazonBuilderUtil.getS3Client();

        // Upload a file as a new object with ContentType and title specified.
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, new File(filePath));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpg");
        //metadata.addUserMetadata("x-amz-meta-title", "someTitle");
        request.setMetadata(metadata);
        return s3Client.putObject(request);
    }

    private String getFileExtension(byte data[]) throws Exception {
        String exe =  URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));
        if(exe.split("/").length > 0)
            exe = exe.split("/")[1];
        return exe.toLowerCase();
    }
}
