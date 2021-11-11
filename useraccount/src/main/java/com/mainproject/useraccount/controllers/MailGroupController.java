package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.services.MailGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class MailGroupController {

    @Autowired
    private MailGroupService mailGroupService;

    @PostMapping("/group/unique")
    public String uniqueGroup(@RequestParam(name="groupName") String groupName)
    {
        return this.mailGroupService.uniqueGroupName(groupName);
    }

    @PostMapping("/group/addGroup")
    public String createGroup(@RequestBody MailGroup mailGroup)
    {
        this.mailGroupService.create(mailGroup);
        return "Added the group successfully";
    }

    @GetMapping("/group/getGroupNames")
    public String[] getGroups()
    {
        return this.mailGroupService.getGroupNames();
    }

    @GetMapping("/group/giveGroupName")
    public String[] giveGroup(@RequestBody Map<Object,String> request)
    {
        return this.mailGroupService.givegroup(request.get("groupName"));
    }

   @DeleteMapping("/group/deleteGroup")
    public String deleteGroup(@RequestParam(name="groupDelete") String groupDelete)
   {
       return this.mailGroupService.deleteOne(groupDelete);
   }

}
