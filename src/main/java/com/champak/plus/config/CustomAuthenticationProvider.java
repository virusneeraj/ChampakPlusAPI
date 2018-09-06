package com.champak.plus.config;

import com.champak.plus.util.MyMongoCollections;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.champak.plus.dao.MongoDBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.regex.Pattern;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    MongoDBDao mongoDBDao;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        DBObject query = new BasicDBObject();
        query.put("email", Pattern.compile("^" + username + "$", Pattern.CASE_INSENSITIVE));
        query.put("password", password);

        try {
            mongoDBDao.findOne(MyMongoCollections.USER,query);
        } catch (IOException e) {
            throw new BadCredentialsException("Get data from DB exception", e);
        }
        if ("externaluser".equals(username) && "pass".equals(password)) {
            return new UsernamePasswordAuthenticationToken
                    (username, password, Collections.emptyList());
        } else {
            throw new
                    BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
