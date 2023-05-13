package com.mainproject.useraccount.controllers;

import com.mainproject.useraccount.entity.MailGroup;
import com.mainproject.useraccount.entity.MailRecipients;
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



    @PostMapping("/group/addGroup")
    public ResponseEntity<?> createGroup(@RequestBody MailGroup mailGroup,String mailAddresses)
    {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName=userDetails.getUsername();
        try{return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.create(mailGroup,userName,mailAddresses));
        }
        catch (Exception e)
        {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }

    }

    @GetMapping("/group/getGroup")
    public ResponseEntity<?> getGroup(@RequestParam(name = "groupId") Long groupId)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.getGroup(userName,groupId));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }

    @GetMapping("/group/getGroups")
    public ResponseEntity<?> getGroups()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.getGroups(userName));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }

    @PutMapping("/group/updateGroup")
    public ResponseEntity<?> updateGroup(@RequestBody MailGroup mailGroup,String mailAddresses){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.updateGroup(userName,mailGroup,mailAddresses));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }

   @DeleteMapping("/group/deleteGroup")
    public ResponseEntity<?> deleteGroup(@RequestBody MailGroup mailGroup)
   {
       UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
               .getPrincipal();
       String userName=userDetails.getUsername();
       try{
           this.mailGroupService.deleteGroup(userName,mailGroup);
           return ResponseEntity.status(HttpStatus.OK).body("Deleted the group successfully");
       }
       catch (Exception e)
       {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
       }
   }

    @DeleteMapping("/group/deleteRecipient")
    public ResponseEntity<?> deleteRecipient(@RequestBody MailGroup mailGroup, @RequestBody MailRecipients mailRecipients)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName=userDetails.getUsername();
        try{
            this.mailGroupService.deleteRecipient(userName,mailGroup,mailRecipients);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Recipient successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
        }
    }

   @GetMapping("/group/suggestions")
    public ResponseEntity<?> suggestions(@RequestParam("prefix") String prefix)
   {
       UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       String username=userDetails.getUsername();
       try{
           return ResponseEntity.status(HttpStatus.OK).body(this.mailGroupService.suggest(username,prefix));
       }
       catch (Exception e)
       {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred");
       }
   }

}
