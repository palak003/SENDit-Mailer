package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.MailGroupRepo;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MailGroupService {

    @Autowired
    private MailGroupRepo mailGroupRepo;

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    public String uniqueGroupName(String groupName,String username) {
        List<UserAuthentication> userList=this.userAuthenticationRepository.findByMailAddress(username);
        UserAuthentication user=userList.get(0);
        List<String> list = this.mailGroupRepo.tempQuery(user.getId());

        if(list.contains(groupName.toUpperCase()))
            return "Please choose another name";
        else
            return "Added the group name now upload the mailAddresses";

    }

    public void create(MailGroup mailGroup, String userName) {
        List<UserAuthentication> userAuthenticationList = userAuthenticationRepository.findByMailAddress(userName);
        UserAuthentication user = userAuthenticationList.get(0);
        MailGroup newEntry = new MailGroup();
        newEntry.setUserAuthentication(user);
        if(CollectionUtils.isEmpty(user.getMailGroupList())){
            user.setMailGroupList(new ArrayList<>());
        }
        user.getMailGroupList().add(newEntry);
        newEntry.setGroupName(mailGroup.getGroupName().toUpperCase());
        String[] mails = mailGroup.getMailAddresses().split(","); // \\n
        ArrayList<String> group = new ArrayList<String>();
        for (String line : mails) {
            String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches())
                group.add(line);
            else
                continue;
        }
        String string = String.join(",", group);
        newEntry.setMailAddresses(string);
        this.mailGroupRepo.save(newEntry);
    }



    public String[] getGroupNames(String username) {
        List<UserAuthentication> userList=this.userAuthenticationRepository.findByMailAddress(username);
        UserAuthentication user=userList.get(0);
        List<String> list = this.mailGroupRepo.tempQuery(user.getId());
        String[] str = new String[list.size()];
        for (int j = 0; j < list.size(); j++) {
            str[j] = list.get(j);
        }
        return str;
    }

    public String[] givegroup(String groupName,String username) {
        List<UserAuthentication> userList=this.userAuthenticationRepository.findByMailAddress(username);
        UserAuthentication user=userList.get(0);
        List<String> list = this.mailGroupRepo.tempQuery(user.getId());

        if(list.contains(groupName.toUpperCase())){
        String str=this.mailGroupRepo.findBygroupNAME(groupName.toUpperCase(),user.getId());
        String[] mails = str.split(",");
        return mails;
        }
        else
            return new String[]{"Please choose valid group name"};

    }

    public String deleteOne(String groupDelete,String username) {

        List<UserAuthentication> userList=this.userAuthenticationRepository.findByMailAddress(username);
        UserAuthentication user=userList.get(0);
        List<String> list = this.mailGroupRepo.tempQuery(user.getId());

        if (list.contains(groupDelete.toUpperCase())){
           MailGroup mailGroup=this.mailGroupRepo.findBygroupname(groupDelete.toUpperCase(),user.getId());
           this.mailGroupRepo.delete(mailGroup);
        return "removed the group successfully";}
        else {
            return "Please choose valid group name";
        }

    }
}

// bcc and scheduling and not saving same recipient mailAddress
