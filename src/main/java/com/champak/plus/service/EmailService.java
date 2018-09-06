package com.champak.plus.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by hp on 11-05-2017.
 */
@Service
public class EmailService {
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${scuapi.email.username}")
    String username;

    @Value("${scuapi.email.password}")
    String password;

    @Value("${scuapi.email.from}")
    String from_email;

    public boolean sendEmail(String to, String subject, String body) {
        logger.trace("sendEmail TO:" + to + " SUBJECT:" + subject);
        boolean isEmailSend = false;

        username = StringUtils.newStringUtf8(Base64.decodeBase64(username));
        password = StringUtils.newStringUtf8(Base64.decodeBase64(password));

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        final String finalUsername = username;
        final String finalPassword = password;
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(finalUsername, finalPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from_email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            isEmailSend = true;

        } catch (MessagingException e) {
            logger.error("SendEmail", e);
        }
        return isEmailSend;
    }
}