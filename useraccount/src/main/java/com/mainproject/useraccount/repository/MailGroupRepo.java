package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.MailGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailGroupRepo extends JpaRepository<MailGroup,String> {


    List<MailGroup> findBygroupName(String groupName);
}
