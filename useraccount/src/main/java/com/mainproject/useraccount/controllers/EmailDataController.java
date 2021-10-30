package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.EmailData;
import com.mainproject.useraccount.services.EmailDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EmailDataController {

    @Autowired
    private EmailDataService emailDataService;

    @GetMapping("/emailData")
    public List<EmailData> getAllData()
    {
        return this.emailDataService.getAll();
    }

    @GetMapping("/emailData/{emailDataId}")
    public Optional<EmailData> getOneData(@PathVariable int emailDataId)
    {
        return this.emailDataService.getOne(emailDataId);
    }


}
