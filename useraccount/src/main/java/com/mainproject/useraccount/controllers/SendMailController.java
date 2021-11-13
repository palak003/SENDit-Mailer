package com.mainproject.useraccount.controllers;


import com.mainproject.useraccount.entity.SendMail;
import com.mainproject.useraccount.services.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins= "*")
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/mail")
    public void firstMail(@RequestBody SendMail mail){
        this.sendMailService.sendmail(mail);
    }
}
