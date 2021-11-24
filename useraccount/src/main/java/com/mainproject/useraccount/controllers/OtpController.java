package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.Otp;
import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.services.OtpMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins= "*")
public class OtpController {

    @Autowired
    private OtpMailService otpMailService;


    @PostMapping("/user/generateOtp")
    public ResponseEntity<?> generateOtp(@RequestBody UserAuthentication details) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.otpMailService.signUp(details));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }

    @PostMapping("/user/validate")
    public ResponseEntity<?> validateOtp(@RequestBody Otp givenOtpDetails) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.otpMailService.validateSignUp(givenOtpDetails));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }


    @PostMapping("/user/forgot")
    public ResponseEntity<?> forgotPass(@RequestBody UserAuthentication forgotdetails)
    {try{
        return ResponseEntity.status(HttpStatus.OK).body( this.otpMailService.forgotPass(forgotdetails));
    }
    catch (Exception e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
    }
    }

    @PostMapping("/user/forgotValidate")
    public ResponseEntity<?> validateForgotOtp(@RequestBody Otp forgotPassDetails){
        try{
            return ResponseEntity.status(HttpStatus.OK).body( this.otpMailService.validateForgotOtp(forgotPassDetails));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }

}



