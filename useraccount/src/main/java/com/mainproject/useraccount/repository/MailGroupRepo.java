package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.MailGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MailGroupRepo extends JpaRepository<MailGroup,String> {

    @Query("SELECT ti from MailGroup ti where ti.groupName=?1 AND ti.userAuthentication.id=?2 ")
    List<MailGroup> findBygroupName(String groupName,int id);

    @Query("SELECT ti.groupName from MailGroup ti where ti.userAuthentication.id=?1")
    List<String> tempQuery(int userId);

    @Query("SELECT ti from MailGroup ti where ti.groupName=?1 AND ti.userAuthentication.id=?2 ")
    MailGroup findBygroupname(String groupDelete,int id);

    @Query("SELECT ti.mailAddresses from MailGroup ti where ti.groupName=?1 AND ti.userAuthentication.id=?2 ")
    String findBygroupNAME(String toUpperCase, int id);

    @Query("SELECT ti.groupName from MailGroup ti where  ti.groupName LIKE ?1% AND ti.userAuthentication.id=?2 ")
    List<String> temp2query(String str,int id);


}
