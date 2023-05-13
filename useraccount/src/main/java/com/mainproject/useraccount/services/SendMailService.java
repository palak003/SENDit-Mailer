package com.mainproject.useraccount.services;


import com.mainproject.useraccount.entity.*;
import com.mainproject.useraccount.repository.MailRepository;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
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
import java.util.Optional;
import java.util.Set;

@Service
public class SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    FileStorageService storageService;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

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


    public String send(NormalMail mail, Set<MailGroup> mailGroupSet,String username) {
        Set<MailRecipients> mailRecipients=null;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        for(MailGroup mailGroup:mailGroupSet){
            mailRecipients.addAll(mailGroup.getMailIds());
        }
        NormalMail newMail=new NormalMail();
        newMail.setSubject(mail.getSubject());
        newMail.setContent(mail.getContent());
        Optional<UserAuthentication> userOptional = this.userAuthenticationRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            newMail.setUserAuthentication(userOptional.get());
        }
        mailRepository.save(newMail);
        String[] recipientsArray = mailRecipients.toArray(new String[0]);
        mailMessage.setTo(recipientsArray);
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getContent());
        javaMailSender.send(mailMessage);
        return "Mail Sent";
    }
    }

