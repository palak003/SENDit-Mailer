package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.MailRecipients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRecipientRepo extends JpaRepository<MailRecipients,Long> {


    MailRecipients findByMailId(String email);
}
