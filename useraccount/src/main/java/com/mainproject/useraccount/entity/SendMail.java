package com.mainproject.useraccount.entity;


import org.springframework.web.multipart.MultipartFile;

public class SendMail {


    public String mailFrom;
    public String[] mailTo;
    public String subject;
    public String content;
   // private MultipartFile file;



    /*public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }*/



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public String[] getMailTo() {
        return mailTo;
    }

    public void setMailTo(String[] mailTo) {
        this.mailTo = mailTo;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }





}
