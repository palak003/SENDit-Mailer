package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.configuration.JwtTokenUtil;
import com.mainproject.useraccount.services.PremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class PremiumController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PremiumService premiumService;


    @GetMapping("/premium")
    public String getPremium() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        this.premiumService.premium(userName.toLowerCase());
        return "Premium account active";

    }

    @GetMapping("/checkPremium")
    public Boolean checkPremium() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        return this.premiumService.check(userName.toLowerCase());

    }
}
