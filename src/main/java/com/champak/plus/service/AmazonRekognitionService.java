package com.champak.plus.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.champak.plus.util.AmazonBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmazonRekognitionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<TextDetection> detectText(String bucket, String photo){
        AmazonRekognition rekognitionClient = AmazonBuilderUtil.getAmazonRekognition();

        DetectTextRequest request = new DetectTextRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo)
                                .withBucket(bucket)));

            DetectTextResult result = rekognitionClient.detectText(request);
            List<TextDetection> textDetections = result.getTextDetections();
            return textDetections;
        }
}
