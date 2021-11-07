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
        //uppercase groupname conditions to be added
        List<MailGroup> list=this.mailGroupRepo.findBygroupName(groupName);
        if (!CollectionUtils.isEmpty(list))
            return "Please choose another name";
        else
            return "Added the group name now upload the mailAddresses";
    }

    public void create(MailGroup mailGroup) {
        MailGroup newEntry=new MailGroup();
        newEntry.setGroupName(mailGroup.getGroupName());
        String[] lines=mailGroup.getMailAddresses().split(","); // \\n
        ArrayList<String> group = new ArrayList<String>();
        for(String line:lines)
        {
            String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            if(matcher.matches())
                group.add(line);
            else
                continue;
        }
        String string = String.join(",", group);
        newEntry.setMailAddresses(string);
        this.mailGroupRepo.save(newEntry);
    }

    public String[] givegroup(String groupName) {
        List<MailGroup> list=this.mailGroupRepo.findBygroupName(groupName);
        MailGroup mailGroup=list.get(0);
        String[] lines= mailGroup.getMailAddresses().split(",");
        return lines;
    }
}
