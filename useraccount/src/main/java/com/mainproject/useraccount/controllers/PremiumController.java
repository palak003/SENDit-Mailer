package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.configuration.JwtTokenUtil;
import com.mainproject.useraccount.services.PremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class PremiumController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PremiumService premiumService;


    @PostMapping("/premium")
    public String getPremium(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {

                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        }

        this.premiumService.premium(userName.toLowerCase());
        return "Premium account active";

    }

    @GetMapping("/checkPremium")
    public String checkPremium(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {

                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        }
        return this.premiumService.check(userName.toLowerCase());

    }
}
