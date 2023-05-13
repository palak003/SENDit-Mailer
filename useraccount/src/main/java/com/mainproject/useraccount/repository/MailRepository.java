package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.NormalMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<NormalMail,Long> {
}
