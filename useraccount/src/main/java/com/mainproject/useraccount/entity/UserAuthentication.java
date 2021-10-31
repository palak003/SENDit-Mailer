package com.mainproject.useraccount.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class UserAuthentication {

    @Id
    private int id;

    private String name;
    private  String mailAddress;
    private String password;

    public UserAuthentication(){

    }

    public UserAuthentication(int id,String name,String mailAddress,String password)
    {
        this.id=id;
        this.name=name;
        this.mailAddress=mailAddress;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
