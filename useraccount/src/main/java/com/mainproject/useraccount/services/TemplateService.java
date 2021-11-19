package com.mainproject.useraccount.services;

import com.mainproject.useraccount.configuration.TemplateConfig;
import com.mainproject.useraccount.entity.MailRequest;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import freemarker.template.Template;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class TemplateService  {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateConfig config;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Async("threadPoolTaskExecutor")
    public String sendEmail(MailRequest request, Map<String, Object> model) {
        MimeMessage message = sender.createMimeMessage();
        try {
            String[] bcc=request.getTo();
            InternetAddress[] bccAddress = new InternetAddress[bcc.length];
            for( int i = 0; i < bcc.length; i++ ) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }
            for( int i = 0; i < bccAddress.length; i++) {
                message.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
            }
            String one="email-template1.ftl";
            String two="email-template2.ftl";
            String three="email-template3.ftl";
            String four="email-template4.ftl";

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());



            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(one);
            if(request.getValue()==2)
                template = freeMarkerConfigurer.getConfiguration().getTemplate(two);
            if(request.getValue()==3)
                template = freeMarkerConfigurer.getConfiguration().getTemplate(three);
            if(request.getValue()==4)
                template = freeMarkerConfigurer.getConfiguration().getTemplate(four);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setText(html , true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());
            sender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
           System.out.println("Sending mail failed");
        }

        return "Template-Mail sent successfully!!!";
    }
}
