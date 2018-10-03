package com.champak.plus.controller.home;

import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.champak.plus.model.CustomResponse;
import com.champak.plus.model.champak.ContestForm;
import com.champak.plus.service.AmazonRekognitionService;
import com.champak.plus.service.AmazonS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search/")
public class SerachController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    String bucketName = "virusrekognition";

    @Value("${scuapi.response.success.code}")
    String success_code;

    @Value("${scuapi.response.error.code}")
    String error_code;

    @Value("${scuapi.response.warning.code}")
    String warning_code;

    @Autowired
    AmazonS3Service amazonS3Service;

    @Autowired
    AmazonRekognitionService amazonRekognitionService;

    @GetMapping("search")
    public CustomResponse serach(@RequestParam("bucket") String bucket, @RequestParam("file") String fileName) {
        logger.info("bucket: "+bucket + " file: "+fileName);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");
        try {
            List<TextDetection> testList = amazonRekognitionService.detectText(bucket, fileName);
            customResponse.setData(testList);
        } catch (Exception e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        }
        return customResponse;
    }

    @PostMapping("upload")
    public CustomResponse submitContest(@RequestBody ContestForm contestForm){
        logger.info("upload request received");
        logger.info("submit request received contestForm:"+contestForm.toString());
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");

        try {
            PutObjectResult putObjectResult = amazonS3Service.uploadFile(contestForm.getFileData().getFile(), contestForm.getFileData().getId());
            customResponse.setData(putObjectResult);
        } catch (Exception e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        }

        return customResponse;
    }

    @PostMapping("getquestion")
    public CustomResponse getQuestion(@RequestBody ContestForm contestForm){
        logger.info("getQuestion request received");
        logger.info("getQuestion request received contestForm:"+contestForm.toString());
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("Your response has been recorded.");

        try {
            PutObjectResult putObjectResult = amazonS3Service.uploadFile(contestForm.getFileData().getFile(), contestForm.getFileData().getId());
            if(putObjectResult != null){
                CustomResponse searchResponse = serach(bucketName, contestForm.getFileData().getId());
                customResponse.setData(searchResponse.getData());
            }else {
                customResponse.setCode(error_code);
                customResponse.setMessage("Couldn't upload file");
            }

            customResponse.setData(putObjectResult);
        } catch (Exception e) {
            e.printStackTrace();
            customResponse.setCode(error_code);
            customResponse.setMessage(e.getMessage());
        }

        return customResponse;
    }
}
