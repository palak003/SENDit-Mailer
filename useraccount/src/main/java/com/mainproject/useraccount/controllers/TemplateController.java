package com.mainproject.useraccount.controllers;


import com.mainproject.useraccount.entity.MailRequest;
import com.mainproject.useraccount.entity.MailResponse;
import com.mainproject.useraccount.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TemplateController {

    @Autowired
    private TemplateService service;

    @PostMapping("/template")
    public MailResponse sendEmail(@RequestBody MailRequest request) {

        Map<String,Object> model=new HashMap<>();
        model.put("Name", request.getName());
        model.put("Location", request.getLocation());
        model.put("Description", request.getDescription());
        model.put("Headline", request.getHeadline());
        model.put("Tagline", request.getTagline());

        return service.sendEmail(request, model);
    }
}
