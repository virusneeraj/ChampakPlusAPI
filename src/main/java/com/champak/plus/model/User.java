package com.champak.plus.model;

import com.champak.plus.util.MyMongoCollections;
import com.champak.plus.util.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "USER")
public class User {
    @Id
    private String id;
    private String email;
    private String country;
    private String fullname;
    private Date createdon;
    private String identifier;
    private String status;
    private Set<UserRole> roles;
    private String password;
    private Date updatedon;
    private MyMongoCollections collection;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(Date updatedon) {
        this.updatedon = updatedon;
    }

    public MyMongoCollections getCollection() {
        return collection;
    }

    public void setCollection(MyMongoCollections collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "{\"User\":{"
                + "                        \"id\":\"" + id + "\""
                + ",                         \"email\":\"" + email + "\""
                + ",                         \"country\":\"" + country + "\""
                + ",                         \"fullname\":\"" + fullname + "\""
                + ",                         \"createdon\":" + createdon
                + ",                         \"identifier\":\"" + identifier + "\""
                + ",                         \"status\":\"" + status + "\""
                + ",                         \"roles\":" + roles
                + ",                         \"password\":\"" + password + "\""
                + ",                         \"updatedon\":" + updatedon
                + ",                         \"collection\":\"" + collection + "\""
                + "}}";
    }
}
