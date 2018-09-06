package com.champak.plus.model;

public class Message {
    private String course;
    private String email;
    private String query;
    private String subject;
    private String name;
    private String country;
    private String mobile;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "{\"Message\":{"
                + "                        \"course\":\"" + course + "\""
                + ",                         \"email\":\"" + email + "\""
                + ",                         \"query\":\"" + query + "\""
                + ",                         \"subject\":\"" + subject + "\""
                + ",                         \"name\":\"" + name + "\""
                + ",                         \"country\":\"" + country + "\""
                + ",                         \"mobile\":\"" + mobile + "\""
                + "}}";
    }
}
