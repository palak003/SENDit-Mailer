package com.mainproject.useraccount.controllers;


import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.entity.NormalMail;
import com.mainproject.useraccount.entity.SendMail;
import com.mainproject.useraccount.services.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins= "*")
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@ModelAttribute SendMail mail) {
        String message = "";
        try {
            this.sendMailService.sendmail(mail);
    return ResponseEntity.status(HttpStatus.OK).body("Mail Sent");
        } catch (Exception e) {
            message = "Could not upload the file!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }

    }

    @PostMapping("/mail")
    public String mail(@RequestBody NormalMail mail, @RequestBody Set<MailGroup> mailGroupSet){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=userDetails.getUsername();
        return this.sendMailService.send(mail,mailGroupSet,username);
    }

}
