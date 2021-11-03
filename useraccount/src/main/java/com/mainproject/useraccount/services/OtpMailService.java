package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.Login;
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

    UserAuthentication user=new UserAuthentication();
    Login userLogin =new Login();

    public String signUp(UserAuthentication userDetails)
    {
        /*if(!isValid(userDetails.getMailAddress(), userDetails.getPassword())){
            return "invalid mail-id/password";
        }
        else{*/
            user=userDetails;
            if (!CollectionUtils.isEmpty(this.otpRepo.findByMailAddress(userDetails.getMailAddress()))) {
                return "You already have an account please Login";
            } else {
                int otp = otpService.generateOTP(userDetails.getMailAddress());
                this.sendOtpMail(userDetails.getMailAddress(), otp);
                return "Otp Sent";
            }
        //}
    }

    public String validateSignUp(int givenOtp){
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

    public void sendOtpMail(String mail, int otp) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("first otp");
            mimeMessageHelper.setFrom(new InternetAddress("goelpalak003@gmail.com", "PalakMailer.com"));
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setText("the otp is " + otp);

            javaMailSender.send(mimeMessage);


        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String forgotPass(Login forgotDetails)
    { /*if(!isValid(forgotDetails.getMailAddress(),forgotDetails.getPassword())){
        return "invalid mail-id/password";
    }
    else{*/
         userLogin = forgotDetails;
        if(CollectionUtils.isEmpty(this.otpRepo.findByMailAddress(userLogin.getMailAddress()))){
            return "seems like you dont have any account";
        }
        else {
            int forgotPassOtp = otpService.generateOTP(userLogin.getMailAddress());
            this.sendOtpMail(userLogin.getMailAddress(), forgotPassOtp);
            return "Otp Sent";
        }
   // }
    }

    public String validateForgotOtp(int givenOtp) {
        final String SUCCESS = "Entered Otp is valid";

        final String FAIL = "Entered Otp is NOT valid. Please Retry!";


        if(givenOtp>= 0){
            int serverForgotOtp = otpService.getOtp(userLogin.getMailAddress());


            if(serverForgotOtp > 0){
                if(givenOtp== serverForgotOtp){

                    UserAuthentication updateUser= this.otpRepo.findByMailAddress(userLogin.getMailAddress()).get(0);
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


    public boolean isValid(String mailAddress,String password)
    {
        String regex1 = "/^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$/";
        Pattern pattern1=Pattern.compile(regex1);
        Matcher matcher1=pattern1.matcher(mailAddress);
        String regex2= "/^[a-zA-Z0-9@#!$%^_]{8,12}$/";
        Pattern pattern2 =Pattern.compile(regex2);
        Matcher matcher2=pattern2.matcher(password);

        return (matcher1.matches()&& matcher2.matches());


    }


}
