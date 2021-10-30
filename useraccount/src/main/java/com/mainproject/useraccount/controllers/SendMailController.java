package com.mainproject.useraccount.controllers;


import com.mainproject.useraccount.entity.SendMail;
import com.mainproject.useraccount.services.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/mail")
    public void firstMail(@RequestBody SendMail mail){
        this.sendMailService.sendmail(mail);
    }
}
