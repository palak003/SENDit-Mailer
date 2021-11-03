package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.Login;
import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.services.OtpMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins= "http://localhost:3000")
public class OtpController {

    @Autowired
    private OtpMailService otpMailService;

    @PostMapping("/user/generateOtp")
    public String generateOtp(@RequestBody UserAuthentication details) {
      return this.otpMailService.signUp(details);
    }

    @PostMapping("/user/validate")
    public String validateOtp(@RequestBody Map<String,Integer> request) {
        int givenOtp = request.get("givenOtp");
        return this.otpMailService.validateSignUp(givenOtp);
    }


    @PostMapping("/user/forgot")
    public String forgotPass(@RequestBody Login forgotdetails)
    {
        return this.otpMailService.forgotPass(forgotdetails);
    }

    @PostMapping("/user/forgotValidate")
    public String validateForgotOtp(@RequestBody Map<String,Integer> request){
        int givenOtp=request.get("givenOtp");
        return this.otpMailService.validateForgotOtp(givenOtp);
    }

}



