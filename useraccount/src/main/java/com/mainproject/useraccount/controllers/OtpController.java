package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.Login;
import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import com.mainproject.useraccount.services.OtpMailService;
import com.mainproject.useraccount.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class OtpController {

    @Autowired
    private OtpMailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserAuthenticationRepository otpRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    UserAuthentication user=new UserAuthentication();
    Login userLogin =new Login();


    @PostMapping("/user/generateOtp")
    public String generateOtp(@RequestBody UserAuthentication details) {
        user=details;
        if(!CollectionUtils.isEmpty(this.otpRepo.findByMailAddress(user.getMailAddress()))){
            return "You already have an account please Login";
        }
        else {
            int otp = otpService.generateOTP(user.getMailAddress());
            this.emailService.sendOtpMail(user.getMailAddress(), otp);
            return "Otp Sent";
        }
    }

    @PostMapping("/user/forgot")
    public String forgotPass(@RequestBody Login details)
    {
        userLogin=details;
        if(CollectionUtils.isEmpty(this.otpRepo.findByMailAddress(userLogin.getMailAddress()))){
            return "seems like you dont have any account";
        }
        else {
            int forgotPassOtp = otpService.generateForgotOtp(userLogin.getMailAddress());
            this.emailService.sendOtpMail(userLogin.getMailAddress(), forgotPassOtp);
            return "Otp Sent";
        }


    }


    @GetMapping("/user/validate")
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
                    this.otpRepo.save(newUser);

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


    @GetMapping("/user/forgotValidate")
    public String validateForgotOtp(@RequestParam(name="forgotOtp") int forgotOtp){
        final String SUCCESS = "Entered Otp is valid";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";


        if(forgotOtp>= 0){
            int serverForgotOtp = otpService.getOtp(userLogin.getMailAddress());

            /*System.out.println(userLogin.getMailAddress());
            System.out.println(serverForgotOtp);
            System.out.println(forgotOtp);*/

            if(serverForgotOtp > 0){
                if(forgotOtp== serverForgotOtp){

                    UserAuthentication updateUser= this.otpRepo.findByMailAddress(userLogin.getMailAddress()).get(0);
                    System.out.println(updateUser.getName());
                    updateUser.setPassword(passwordEncoder.encode(userLogin.getPassword()));
                    this.otpRepo.save(updateUser);

                    otpService.clearOTP(userLogin.getMailAddress());
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



