package com.champak.plus.util;

import com.amazonaws.services.rekognition.model.TextDetection;

import java.util.List;

public class CommonUtil {
    public static String getQuestion(List<TextDetection> textDetections){
        String question = "";
        for (TextDetection text: textDetections) {
            if (text.getDetectedText().length() > 30)
                question += " "+text.getDetectedText();
            else if(text.getDetectedText().endsWith("?")){
                if(!question.contains(text.getDetectedText()))
                    question += " "+ text.getDetectedText();
            }
            else if(text.getDetectedText().endsWith(".")){
                if(!question.contains(text.getDetectedText()))
                    if(!text.getDetectedText().startsWith("\""))
                        question += " "+ text.getDetectedText();
            }
            /*else if(text.getDetectedText().endsWith("!")){
                if(!question.contains(text.getDetectedText()))
                    question += " "+ text.getDetectedText();
            }*/

        }
        return question;
    }
}
