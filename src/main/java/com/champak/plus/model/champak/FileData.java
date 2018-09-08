package com.champak.plus.model.champak;

import java.util.Arrays;

public class FileData {
    private String id;
    private String metaData;
    private byte[] file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "{\"FileData\":{"
                + "                        \"id\":\"" + id + "\""
                + ",                         \"metaData\":\"" + metaData + "\""
                + ",                         \"file\":" + Arrays.toString(file)
                + "}}";
    }
}
