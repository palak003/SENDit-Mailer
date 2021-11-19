package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Component
public class SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async("threadPoolTaskExecutor")
    public String sendmail(SendMail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            String[] bcc=mail.getMailTo();
            InternetAddress[] bccAddress = new InternetAddress[bcc.length];
            for( int i = 0; i < bcc.length; i++ ) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }
            for( int i = 0; i < bccAddress.length; i++) {
                mimeMessage.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
            }
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            if(mail.getAttachment()=="")
                return "Please enter the file name";

            String path="C:\\Users\\Pearl\\Desktop\\SENDit-Mailer\\uploads\\"+mail.getAttachment();
            FileSystemResource file = new FileSystemResource(new File(path));
            mimeMessageHelper.addAttachment(mail.getName()+".pdf", file);

            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(mail.getMailFrom());
            mimeMessageHelper.setText(mail.getContent());

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Mail Sent!!!";
    }
}
