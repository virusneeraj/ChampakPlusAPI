package com.champak.plus.model;

public class CustomQuery {
    private String key;
    private Object value;
    private String type;
    private Boolean caseSensitive;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean getCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{\"CustomQuery\":{"
                + "                        \"key\":\"" + key + "\""
                + ",                         \"value\":" + value
                + ",                         \"type\":\"" + type + "\""
                + ",                         \"caseSensitive\":\"" + caseSensitive + "\""
                + "}}";
    }
}
