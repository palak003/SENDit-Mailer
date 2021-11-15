package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.configuration.JwtTokenUtil;
import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.services.MailGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class MailGroupController {

    @Autowired
    private MailGroupService mailGroupService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/group/unique")
    public String uniqueGroup(@RequestBody Map<Object,String> req, HttpServletRequest request)
    {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName=null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        }
        return this.mailGroupService.uniqueGroupName(req.get("groupName"),userName);
    }

    @PostMapping("/group/addGroup")
    public String createGroup(@RequestBody MailGroup mailGroup, HttpServletRequest request)
    {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName=null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        }

        this.mailGroupService.create(mailGroup,userName);
        return "Added the group successfully";
    }

    @GetMapping("/group/getGroupNames")
    public String[] getGroups(HttpServletRequest request)
    {

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName=null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        }
        return this.mailGroupService.getGroupNames(userName);
    }

    @GetMapping("/group/giveGroupName")
    public String[] giveGroup(@RequestParam(name="groupName") String groupName,HttpServletRequest request)
    {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName=null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        }
        return this.mailGroupService.givegroup(groupName,userName);
    }

   @DeleteMapping("/group/deleteGroup")
    public String deleteGroup(@RequestBody Map<Object,String> req,HttpServletRequest request)
   {
       final String requestTokenHeader = request.getHeader("Authorization");
       String jwtToken = null;
       String userName=null;
       if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
           jwtToken = requestTokenHeader.substring(7);
           try {
               userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
           } catch (IllegalArgumentException e) {
               System.out.println("Unable to get JWT Token");
           }
       }
       return this.mailGroupService.deleteOne(req.get("groupDelete"),userName);
   }

}
