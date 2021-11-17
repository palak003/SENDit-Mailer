package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.configuration.JwtTokenUtil;
import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import com.mainproject.useraccount.services.MailGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;


   @PostMapping("/group/manageGroups")
    public ResponseEntity<String> manageGroups(HttpServletRequest request) {
       final String requestTokenHeader = request.getHeader("Authorization");
       String jwtToken = null;
       String userName = null;
       if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
           jwtToken = requestTokenHeader.substring(7);
           try {
               userName = jwtTokenUtil.getUsernameFromToken(jwtToken);

           } catch (IllegalArgumentException e) {
               System.out.println("Unable to get JWT Token");
           }
           return ResponseEntity.status(HttpStatus.OK).body("allowed");
       }
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not allowed");
   }

   /*what if the user saw the endpoints and enter this page without permission and access other apis....they will give
    the error of unauthorised*/



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

        return this.mailGroupService.uniqueGroupName(req.get("groupName"),userName.toLowerCase());
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

        this.mailGroupService.create(mailGroup,userName.toLowerCase());
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
        return this.mailGroupService.getGroupNames(userName.toLowerCase());
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
        return this.mailGroupService.givegroup(groupName,userName.toLowerCase());
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
       return this.mailGroupService.deleteOne(req.get("groupDelete"),userName.toLowerCase());
   }

}
