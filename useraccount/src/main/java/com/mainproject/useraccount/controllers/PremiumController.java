package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.configuration.JwtTokenUtil;
import com.mainproject.useraccount.services.PremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getPremium() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        this.premiumService.premium(userName.toLowerCase());
        try{
            return ResponseEntity.status(HttpStatus.OK).body("Premium account active");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

    }

    @GetMapping("/checkPremium")
    public ResponseEntity<?> checkPremium() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.premiumService.check(userName.toLowerCase()));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}
