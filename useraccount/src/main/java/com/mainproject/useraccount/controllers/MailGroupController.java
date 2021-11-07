package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.services.MailGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailGroupController {

    @Autowired
    private MailGroupService mailGroupService;

    @PostMapping("/unique")
    public String uniqueGroup(@RequestParam(name="groupName") String groupName)
    {
        return this.mailGroupService.uniqueGroupName(groupName);
    }

    @PostMapping("/addGroup")
    public String createGroup(@RequestBody MailGroup mailGroup)
    {
        this.mailGroupService.create(mailGroup);
        return "Added the group successfully";
    }

    @GetMapping("/giveGroupName")
    public String[] giveGroup(@RequestParam(name="groupName") String groupName)
    {
        return this.mailGroupService.givegroup(groupName);
    }
}
