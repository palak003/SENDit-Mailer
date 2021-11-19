package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.services.MailGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class MailGroupController {

    @Autowired
    private MailGroupService mailGroupService;



    @PostMapping("/group/unique")
    public String uniqueGroup(@RequestBody Map<Object, String> req)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        return this.mailGroupService.uniqueGroupName(req.get("groupName"),userName.toLowerCase());
    }


    @PostMapping("/group/addGroup")
    public String createGroup(@RequestBody MailGroup mailGroup)
    {
        /*final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName=null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        }*/
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        this.mailGroupService.create(mailGroup,userName.toLowerCase());
        return "Added the group successfully";
    }

    @GetMapping("/group/getGroupNames")
    public String[] getGroups()
    {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        return this.mailGroupService.getGroupNames(userName.toLowerCase());
    }

    @GetMapping("/group/giveGroupName")
    public String[] giveGroup(@RequestParam(name = "groupName") String groupName)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        return this.mailGroupService.givegroup(groupName,userName.toLowerCase());
    }

   @DeleteMapping("/group/deleteGroup")
    public String deleteGroup(@RequestBody Map<Object, String> req)
   {
       UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
               .getPrincipal();
       String userName=userDetails.getUsername();
       return this.mailGroupService.deleteOne(req.get("groupDelete"),userName.toLowerCase());
   }

}
