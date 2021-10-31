package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import com.mainproject.useraccount.services.OtpMailService;
import com.mainproject.useraccount.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OtpController {
    @Autowired
    private OtpMailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

List<UserAuthentication> list=new ArrayList<>();
    UserAuthentication user=new UserAuthentication();
    int otp;

    @PostMapping("/generateOtp")
    public UserAuthentication generateOtp(@RequestBody UserAuthentication details) {
        user=details;
        otp = otpService.generateOTP(user.getMailAddress());
        this.emailService.sendOtpMail(details, otp);
        return user;
    }



    @GetMapping("/validate")
    public String validateOtp(@RequestParam(name="givenOtp") int givenOtp)
    {
        final String SUCCESS = "Entered Otp is valid";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";


        if(givenOtp >= 0){
            int serverOtp = otpService.getOtp(user.getMailAddress());


            if(serverOtp > 0){
                if(givenOtp== serverOtp){
                    UserAuthentication newUser=new UserAuthentication();
                    newUser.setId(user.getId());
                    newUser.setName(user.getName());
                    newUser.setMailAddress(user.getMailAddress());
                    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    this.userAuthenticationRepository.save(newUser);

                    otpService.clearOTP(user.getMailAddress());
                    return ("Entered Otp is valid");
                }else{
                    return SUCCESS;
                }
            }else {
                return FAIL;
            }
        }else {
            return FAIL;
        }
    }


}





