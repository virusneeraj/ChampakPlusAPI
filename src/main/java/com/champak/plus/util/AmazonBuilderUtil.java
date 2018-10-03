package com.champak.plus.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AmazonBuilderUtil {

    static String clientRegion = "us-east-2";
    String bucketName = "virusrekognition";

    public static BasicAWSCredentials getBasicAWSCredentials(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIU6HPJKCT7EWTLKA", "pmTKpmYNZKpU+hUBVMzujztyTLKw5N301F+SVnq9");
        return awsCreds;
    }

    public static AmazonS3 getS3Client(){
        BasicAWSCredentials awsCreds = getBasicAWSCredentials();
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
        .withRegion(clientRegion)
        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
        .build();
        return  s3Client;
    }

    public static AmazonRekognition getAmazonRekognition(){
        BasicAWSCredentials awsCreds = getBasicAWSCredentials();
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                .withRegion(clientRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        return rekognitionClient;
    }
}
