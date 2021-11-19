package com.mainproject.useraccount.controllers;


import com.mainproject.useraccount.entity.SendMail;
import com.mainproject.useraccount.services.FileStorageService;
import com.mainproject.useraccount.services.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins= "*")
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    FileStorageService storageService;

    @PostMapping("/mail")
    public String firstMail(@RequestBody SendMail mail){
        return this.sendMailService.sendmail(mail);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


}
