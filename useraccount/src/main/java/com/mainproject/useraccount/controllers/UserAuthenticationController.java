package com.mainproject.useraccount.controllers;


import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserAuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @GetMapping("/signup")
    public List<UserAuthentication> getAllmails()
    {
        return this.userAuthenticationService.getAll();
    }

    @GetMapping("/signup/{personId}")
    public Optional<UserAuthentication> getOneMail(@PathVariable int personId)
    {
        return this.userAuthenticationService.getOne(personId);
    }

    @PostMapping("/signup")
    public void createOne(@RequestBody UserAuthentication details)
    {
        this.userAuthenticationService.createAccount(details);
    }

}
