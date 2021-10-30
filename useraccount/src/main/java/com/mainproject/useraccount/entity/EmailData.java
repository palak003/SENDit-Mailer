package com.mainproject.useraccount.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmailData {
    @Id
private int emailDataId;
private String emailId;
private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmailDataId() {
        return emailDataId;
    }

    public void setEmailDataId(int emailDataId) {
        this.emailDataId = emailDataId;
    }
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
