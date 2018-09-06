package com.champak.plus.util;

import com.champak.plus.model.CustomResponse;
import com.champak.plus.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${scuapi.response.success.code}")
    String success_code;

    @Value("${scuapi.response.error.code}")
    String error_code;

    @Value("${scuapi.response.warning.code}")
    String warning_code;

    //validate user, if user is invalid then return warning code
    public CustomResponse validateUser(User user) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(success_code);
        customResponse.setMessage("User validation success");

        if (user.getEmail() == null) {
            customResponse.setCode(warning_code);
            customResponse.setMessage("Email is mandatory");
            return customResponse;
        }else if (!user.getEmail().contains("@")) {
            customResponse.setCode(warning_code);
            customResponse.setMessage("Invalid email format");
            return customResponse;
        }

        if (user.getCollection() == null) {
            customResponse.setCode(warning_code);
            customResponse.setMessage("Collection/App name is mandatory");
            return customResponse;
        }

        if (user.getRoles() == null) {
            customResponse.setCode(warning_code);
            customResponse.setMessage("At least one role is mandatory");
            return customResponse;
        }
        return customResponse;
    }

    //bind user template with user entity
    public String templateBinding(String templeteStr, User user){
        ObjectMapper mapper = new ObjectMapper();
        // Convert POJO to Map
        Map<String, Object> map = mapper.convertValue(user, new TypeReference<Map<String, Object>>() {});

        for(String key : map.keySet()){
            Object value = map.get(key);
            key = "##"+key+"##";
            templeteStr = templeteStr.replaceAll(key,""+value);
        }
        return templeteStr;
    }
}
