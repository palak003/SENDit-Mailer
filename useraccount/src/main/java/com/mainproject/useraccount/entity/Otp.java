package com.mainproject.useraccount.entity;




public class Otp {

    private int givenOtp;
    private String name;
    private  String mailAddress;
    private String password;


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

    public int getOtp() {
        return givenOtp;
    }

    public void setOtp(int givenOtp) {
        this.givenOtp = givenOtp;
    }
}
