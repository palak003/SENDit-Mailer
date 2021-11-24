package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.services.MailGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> uniqueGroup(@RequestBody Map<String, String> req)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.uniqueGroupName(req.get("groupName"),userName.toLowerCase()));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }


    @PostMapping("/group/addGroup")
    public ResponseEntity<?> createGroup(@RequestBody MailGroup mailGroup)
    {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.create(mailGroup,userName.toLowerCase()));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }

    }

    @GetMapping("/group/getGroupNames")
    public ResponseEntity<?> getGroups()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.getGroupNames(userName.toLowerCase()));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }

    @GetMapping("/group/giveGroupName")
    public ResponseEntity<?> giveGroup(@RequestParam(name = "groupName") String groupName)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.givegroup(groupName,userName.toLowerCase()));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }

    }

   @DeleteMapping("/group/deleteGroup")
    public ResponseEntity<?> deleteGroup(@RequestBody Map<String, String> req)
   {
       UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
               .getPrincipal();
       String userName=userDetails.getUsername();
       try{
           return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.deleteOne(req.get("groupDelete"),userName.toLowerCase()));
       }
       catch (Exception e)
       {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
       }
   }

   @GetMapping("/group/suggestions")
    public ResponseEntity<?> suggestions(@RequestBody Map<String,String> req)
   {
       UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       String userName=userDetails.getUsername();
       try{
           return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.suggest(req.get("groupInitial"),userName.toLowerCase()));
       }
       catch (Exception e)
       {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
       }
   }

}
