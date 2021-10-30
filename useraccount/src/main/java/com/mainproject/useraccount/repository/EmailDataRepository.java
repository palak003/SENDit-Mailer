package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDataRepository extends JpaRepository<EmailData,Integer> {
}
