package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.entity.MailRecipients;
import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.MailGroupRepo;
import com.mainproject.useraccount.repository.MailRecipientRepo;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MailGroupService {

    @Autowired
    private MailGroupRepo mailGroupRepo;
    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;
    @Autowired
    private MailRecipientRepo mailRecipientRepo;

    public MailGroup create(MailGroup mailGroup, String userName, String mailAddresses) {
        Optional<UserAuthentication> userOptional = userAuthenticationRepository.findByUsername(userName);
        MailGroup mail = new MailGroup();
        if (userOptional.isPresent()) {
            UserAuthentication userAuthentication = userOptional.get();
            mail.setGroupName(mailGroup.getGroupName());
            String[] mailRecipients = mailAddresses.split("\\r\\n");
            for (String email : mailRecipients) {
                if(!isValidEmail(email))
                    continue;
                MailRecipients recipients = mailRecipientRepo.findByMailId(email);
                if (recipients == null) {
                    recipients = new MailRecipients();
                    recipients.setMailId(email);
                    mailRecipientRepo.save(recipients);
                }
                mail.getMailIds().add(recipients);
            }
            mailGroupRepo.save(mail);
            userAuthentication.getMailGroups().add(mailGroup);
            userAuthenticationRepository.save(userAuthentication);
        }
        return mail;
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


   public Set<MailGroup> getGroups(String username) {
       Optional<UserAuthentication> userOptional = this.userAuthenticationRepository.findByUsername(username);
       Set<MailGroup> mailGroups = null;
       if (userOptional.isPresent()) {
           mailGroups=userOptional.get().getMailGroups();
       }
       return mailGroups;
       }

   public MailGroup getGroup(String username,Long id){
       Optional<UserAuthentication> userOptional = this.userAuthenticationRepository.findByUsername(username);
       if (userOptional.isPresent()) {
           return mailGroupRepo.findById(id);
       }
       return null;
   }

   public MailGroup updateGroup(String username,MailGroup mail,String mailAddresses){
       Optional<UserAuthentication> userOptional = this.userAuthenticationRepository.findByUsername(username);
       MailGroup mailGroup=mailGroupRepo.findById(mail.getId());
       if (userOptional.isPresent()) {
           String[] mailRecipients = mailAddresses.split("\\r\\n");
           for (String email : mailRecipients) {
               if(!isValidEmail(email))
                   continue;
               MailRecipients recipients = mailRecipientRepo.findByMailId(email);
               if (recipients == null) {
                   recipients = new MailRecipients();
                   recipients.setMailId(email);
                   mailRecipientRepo.save(recipients);
               }
               mail.getMailIds().add(recipients);
           }
           mailGroupRepo.save(mail);
       }
       return mailGroup;
   }


   public void deleteGroup(String username,MailGroup mail){
       Optional<UserAuthentication> userOptional = this.userAuthenticationRepository.findByUsername(username);
       MailGroup mailGroup=mailGroupRepo.findById(mail.getId());
       UserAuthentication userAuthentication=userOptional.get();
       userAuthentication.getMailGroups().remove(mailGroup);
      for(MailRecipients recipients:mailGroup.getMailIds()){
          recipients.getMailGroups().remove(mailGroup);
          mailRecipientRepo.save(recipients);
      }
   }

   public void deleteRecipient(String username,MailGroup mail,MailRecipients mailRecipient){
       Optional<UserAuthentication> userOptional = this.userAuthenticationRepository.findByUsername(username);
       MailGroup mailGroup=mailGroupRepo.findById(mail.getId());
       UserAuthentication userAuthentication=userOptional.get();
       mailGroup.getMailIds().remove(mailRecipient);
       if(mailRecipient.getMailGroups().isEmpty())
           mailRecipientRepo.delete(mailRecipient);
       mailGroupRepo.save(mailGroup);
   }


    public Set<MailGroup> suggest(String username,String prefix) {
        Optional<UserAuthentication> userOptional = this.userAuthenticationRepository.findByUsername(username);
        Set<MailGroup> mailGroups=null;
        if (userOptional.isPresent()) {
            UserAuthentication userAuthentication = userOptional.get();
            Long userId = userAuthentication.getId();
           mailGroups= mailGroupRepo.findByNameStartingWithAndUserId(prefix, userId);
        }
       return mailGroups;
    }

   }





