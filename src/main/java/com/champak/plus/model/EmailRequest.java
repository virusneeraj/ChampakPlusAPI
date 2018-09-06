package com.champak.plus.model;

import java.util.Arrays;

public class EmailRequest {
    private String to;
    private String cc;
    private String subject;
    private String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{\"EmailRequest\":{"
                + "                        \"to\":\"" + to + "\""
                + ",                         \"cc\":\"" + cc + "\""
                + ",                         \"subject\":\"" + subject + "\""
                + ",                         \"body\":\"" + body + "\""
                + "}}";
    }
}
