package com.mainproject.useraccount.services;


import com.mainproject.useraccount.entity.NormalMail;
import com.mainproject.useraccount.entity.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    FileStorageService storageService;

    @Async("threadPoolTaskExecutor")
    public String sendmail(SendMail mail) {


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            storageService.save(mail.getFile());
                String[] bcc = mail.getMailTo();
                InternetAddress[] bccAddress = new InternetAddress[bcc.length];
                for (int i = 0; i < bcc.length; i++) {
                    bccAddress[i] = new InternetAddress(bcc[i]);
                }
                for (int i = 0; i < bccAddress.length; i++) {
                    mimeMessage.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
                }
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                String path = "C:\\Users\\Pearl\\Desktop\\SENDit-Mailer\\useraccount\\src\\main\\resources\\uploads\\" +mail.getFile().getOriginalFilename();
                FileSystemResource fileToBeAttached = new FileSystemResource(new File(path));
                mimeMessageHelper.addAttachment(mail.getFile().getOriginalFilename(), fileToBeAttached);
                mimeMessageHelper.setSubject(mail.getSubject());
                mimeMessageHelper.setFrom(String.valueOf(new InternetAddress("mailersendit@gmail.com")), "SENDit");
                mimeMessageHelper.setText(mail.getContent());
                javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Mail Sent";
    }


    public String send(NormalMail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            String[] bcc = mail.getMailTo();
            InternetAddress[] bccAddress = new InternetAddress[bcc.length];
            for (int i = 0; i < bcc.length; i++) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }
            for (int i = 0; i < bccAddress.length; i++) {
                mimeMessage.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
            }
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(String.valueOf(new InternetAddress("mailersendit@gmail.com")), "SENDit");
            mimeMessageHelper.setText(mail.getContent());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Mail Sent";
    }
    }

