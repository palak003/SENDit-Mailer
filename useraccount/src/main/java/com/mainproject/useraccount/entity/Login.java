package com.mainproject.useraccount.entity;

public class Login {
    private String mailAddress;
    private String password;


    public Login(){}

    public Login(String mailId,String password)
    {
        this.mailAddress=mailId;
        this.password=password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailId(String mailId) {
        this.mailAddress = mailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


