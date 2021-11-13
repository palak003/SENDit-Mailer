package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.repository.MailGroupRepo;
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

    public String uniqueGroupName(String groupName) {
        List<MailGroup> list = this.mailGroupRepo.findBygroupName(groupName.toUpperCase());
        if (!CollectionUtils.isEmpty(list))
            return "Please choose another name";
        else
            return "Added the group name now upload the mailAddresses";
    }

    public void create(MailGroup mailGroup) {
        MailGroup newEntry = new MailGroup();
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



    public String[] getGroupNames() {
        List<String> list = this.mailGroupRepo.tempQuery();
        String[] str = new String[list.size()];
        for (int j = 0; j < list.size(); j++) {
            str[j] = list.get(j);
        }
        return str;
    }

    public String[] givegroup(String groupName) {
        List<MailGroup> list = this.mailGroupRepo.findBygroupName(groupName.toUpperCase());

        if(!CollectionUtils.isEmpty(list)){
        MailGroup mailGroup = list.get(0);
        String[] mails = mailGroup.getMailAddresses().split(",");
        return mails;}
        else
            return new String[]{"Please choose valid group name"};

    }

    public String deleteOne(String groupDelete) {
        List<MailGroup> list = this.mailGroupRepo.findBygroupName(groupDelete.toUpperCase());
        if (!CollectionUtils.isEmpty(list)){
            MailGroup mailGroup = list.get(0);
            this.mailGroupRepo.delete(mailGroup);
        return "removed the group successfully";}
        else {
            return "Please choose valid group name";
        }

    }
}
