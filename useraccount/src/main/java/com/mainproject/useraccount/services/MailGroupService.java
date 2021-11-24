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
if(groupName=="")
    return "Please enter a group Name";

else{
            List<UserAuthentication> userList = this.userAuthenticationRepository.findByMailAddress(username);
            UserAuthentication user = userList.get(0);
            List<String> list = this.mailGroupRepo.tempQuery(user.getId());
            if (list.contains(groupName.toUpperCase()))
                return "Please choose another name";
            else {
                MailGroup newEntry = new MailGroup();
                newEntry.setUserAuthentication(user);
                if(CollectionUtils.isEmpty(user.getMailGroupList())){
                    user.setMailGroupList(new ArrayList<>());
                }
                user.getMailGroupList().add(newEntry);
                newEntry.setGroupName(groupName.toUpperCase());
                this.mailGroupRepo.save(newEntry);
                return "Added the groupName successfully now upload the mailAddresses";
            }
        }
}


    public String create(MailGroup mailGroup, String userName) {
        List<UserAuthentication> userAuthenticationList = userAuthenticationRepository.findByMailAddress(userName);
        UserAuthentication user = userAuthenticationList.get(0);
        if(!CollectionUtils.isEmpty(this.mailGroupRepo.findBygroupName(mailGroup.getGroupName().toUpperCase(), user.getId()))) {
            MailGroup newEntry=this.mailGroupRepo.findBygroupName(mailGroup.getGroupName().toUpperCase(),user.getId()).get(0);
            String[] lines = mailGroup.getMailAddresses().split("\\r\\n"); // \\n
            ArrayList<String> myList = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {
                lines[i] = lines[i].toLowerCase();
                if (!myList.contains(lines[i]))
                    myList.add(lines[i]);
            }
            ArrayList<String> group = new ArrayList<>();
            for (int i = 0; i < myList.size(); i++) {
                String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(myList.get(i));
                if (matcher.matches())
                    group.add(myList.get(i));
            }

            String string = String.join(",", group);
            newEntry.setMailAddresses(string);
            this.mailGroupRepo.save(newEntry);
            return "Added the group successfully";
        }
        else
            return "GroupName not matched";
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
if(groupDelete=="")
    return "Please enter a group Name to delete";

else{
            List<UserAuthentication> userList = this.userAuthenticationRepository.findByMailAddress(username);
            UserAuthentication user = userList.get(0);
            List<String> list = this.mailGroupRepo.tempQuery(user.getId());
            if (list.contains(groupDelete.toUpperCase())) {
                MailGroup mailGroup = this.mailGroupRepo.findBygroupname(groupDelete.toUpperCase(), user.getId());
                this.mailGroupRepo.delete(mailGroup);
                return "Removed the group successfully";
            } else {
                return "Please choose valid group name";
            }

        }
    }

    public String[] suggest(String groupInitial,String username) {
        List<UserAuthentication> userList=this.userAuthenticationRepository.findByMailAddress(username);
        UserAuthentication user=userList.get(0);
        List<String> list = this.mailGroupRepo.temp2query(groupInitial.toUpperCase(),user.getId());
        String[] str = new String[list.size()];
        for (int j = 0; j < list.size(); j++) {
            str[j] = list.get(j);
        }
        return str;

    }
}




