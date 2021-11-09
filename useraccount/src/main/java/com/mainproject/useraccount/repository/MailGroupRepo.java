package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.MailGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MailGroupRepo extends JpaRepository<MailGroup,String> {


    List<MailGroup> findBygroupName(String groupName);

    @Query("SELECT ti.groupName from MailGroup ti")
    public List<String> tempQuery();
}
