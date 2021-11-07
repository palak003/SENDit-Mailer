package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.Otp;
import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OtpMailService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserAuthenticationRepository otpRepo;




    public String signUp(UserAuthentication userDetails)
    {
        if(!isValid(userDetails.getMailAddress(), userDetails.getPassword())){
            return "invalid mail-id/password";
        }
        else{
            if (!CollectionUtils.isEmpty(this.otpRepo.findByMailAddress(userDetails.getMailAddress()))) {
                return "You already have an account please Login";
            } else {
                int otp = otpService.generateOTP(userDetails.getMailAddress());
                this.sendOtpMail(userDetails.getMailAddress(), otp);
                return "Otp Sent";
            }
        }
    }

    public String validateSignUp(Otp givenOtpdetails){
        final String SUCCESS = "You are registered successfully";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        if(givenOtpdetails.getOtp() >= 0){
            int serverOtp = otpService.getOtp(givenOtpdetails.getMailAddress());
            if(serverOtp > 0){
                if(givenOtpdetails.getOtp()== serverOtp){
                    UserAuthentication newUser=new UserAuthentication();
                    newUser.setName(givenOtpdetails.getName());
                    newUser.setMailAddress(givenOtpdetails.getMailAddress());
                    newUser.setPassword(passwordEncoder.encode(givenOtpdetails.getPassword()));
                    System.out.println(newUser.getMailAddress());
                    this.otpRepo.save(newUser);
                    otpService.clearOTP(givenOtpdetails.getMailAddress());
                    return ("You are registered successfully");
                }else{
                    return FAIL;
                }
            }else {
                return FAIL;
            }
        }else {
            return FAIL;
        }

    }

    public void sendOtpMail(String mail, int otp) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("first otp");
            mimeMessageHelper.setFrom(new InternetAddress("mailersendit@gmail.com", "SEND-itMailer.com"));
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setText("the otp is " + otp);

            javaMailSender.send(mimeMessage);


        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String forgotPass(UserAuthentication forgotDetails)
    { if(!isValid(forgotDetails.getMailAddress(),forgotDetails.getPassword())){
        return "invalid mail-id/password";
    }
    else{
        if(CollectionUtils.isEmpty(this.otpRepo.findByMailAddress(forgotDetails.getMailAddress()))){
            return "seems like you dont have any account";
        }
        else {
            int forgotPassOtp = otpService.generateOTP(forgotDetails.getMailAddress());
            this.sendOtpMail(forgotDetails.getMailAddress(), forgotPassOtp);
            return "Otp Sent";
        }
    }
    }

    public String validateForgotOtp(Otp forgotOtpDetails) {
        final String SUCCESS = "Changed the password successfully";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        if(forgotOtpDetails.getOtp()>= 0){
            int serverForgotOtp = otpService.getOtp(forgotOtpDetails.getMailAddress());
            if(serverForgotOtp > 0){
                if(forgotOtpDetails.getOtp()== serverForgotOtp){

                    UserAuthentication updateUser= this.otpRepo.findByMailAddress(forgotOtpDetails.getMailAddress()).get(0);
                    updateUser.setPassword(passwordEncoder.encode(forgotOtpDetails.getPassword()));
                    this.otpRepo.save(updateUser);

                    otpService.clearOTP(forgotOtpDetails.getMailAddress());
                    return ("Changed the password successfully");
                }else{
                    return FAIL;
                }
            }else {
                return FAIL;
            }
        }else {
            return FAIL;
        }
    }


    public boolean isValid(String mailAddress,String password)
    {
        String regex1 = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern1=Pattern.compile(regex1);
        Matcher matcher1=pattern1.matcher(mailAddress);
        String regex2= "^[a-zA-Z0-9@#!$%^_]{8,12}$";
        Pattern pattern2 =Pattern.compile(regex2);
        Matcher matcher2=pattern2.matcher(password);

        return (matcher1.matches()&& matcher2.matches());


    }


}
