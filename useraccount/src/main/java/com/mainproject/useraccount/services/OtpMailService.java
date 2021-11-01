package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class OtpMailService {

    @Autowired
    private JavaMailSender javaMailSender;

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
}
