package com.champak.plus.model.champak;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Date;

@Document(collection = "USER")
public class ContestForm {
    @Id
    private String id;
    private String candidateRollNo;
    private String candidateName;
    private String schoolName;
    private String candidateClass;
    private String candidateSection;
    private String contestIssue;
    private byte[] file;
    private String fileId;
    private Date createdDate;
    private Date modifiedDate;
    private String spreadsheetId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidateRollNo() {
        return candidateRollNo;
    }

    public void setCandidateRollNo(String candidateRollNo) {
        this.candidateRollNo = candidateRollNo;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCandidateClass() {
        return candidateClass;
    }

    public void setCandidateClass(String candidateClass) {
        this.candidateClass = candidateClass;
    }

    public String getCandidateSection() {
        return candidateSection;
    }

    public void setCandidateSection(String candidateSection) {
        this.candidateSection = candidateSection;
    }

    public String getContestIssue() {
        return contestIssue;
    }

    public void setContestIssue(String contestIssue) {
        this.contestIssue = contestIssue;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        if(modifiedDate!= null)
            return modifiedDate;
        else
            return new Date();
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }

    @Override
    public String toString() {
        return "{\"ContestForm\":{"
                + "                        \"id\":\"" + id + "\""
                + ",                         \"candidateRollNo\":\"" + candidateRollNo + "\""
                + ",                         \"candidateName\":\"" + candidateName + "\""
                + ",                         \"schoolName\":\"" + schoolName + "\""
                + ",                         \"candidateClass\":\"" + candidateClass + "\""
                + ",                         \"candidateSection\":\"" + candidateSection + "\""
                + ",                         \"contestIssue\":\"" + contestIssue + "\""
                + ",                         \"file\":" + Arrays.toString(file)
                + ",                         \"fileId\":\"" + fileId + "\""
                + ",                         \"createdDate\":" + createdDate
                + ",                         \"modifiedDate\":" + modifiedDate
                + ",                         \"spreadsheetId\":\"" + spreadsheetId + "\""
                + "}}";
    }
}
