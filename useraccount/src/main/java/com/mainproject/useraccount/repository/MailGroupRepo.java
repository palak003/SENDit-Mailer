package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.MailGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MailGroupRepo extends JpaRepository<MailGroup,String> {


    List<MailGroup> findBygroupName(String groupName);

    @Query("SELECT ti.groupName from MailGroup ti where ti.userAuthentication.id=?1")
    public List<String> tempQuery(int userId);

    @Query("SELECT ti from MailGroup ti where ti.groupName=?1 AND ti.userAuthentication.id=?2 ")
    MailGroup findBygroupname(String groupDelete,int id);

    @Query("SELECT ti.mailAddresses from MailGroup ti where ti.groupName=?1 AND ti.userAuthentication.id=?2 ")
    String findBygroupNAME(String toUpperCase, int id);
}
