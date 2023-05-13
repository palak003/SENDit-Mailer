package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.MailGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MailGroupRepo extends JpaRepository<MailGroup,String> {


    MailGroup findById(Long id);

    @Query("SELECT mg FROM MailGroup mg JOIN mg.userAuthentications ua WHERE mg.name LIKE :prefix AND ua.id = :userId")
    Set<MailGroup> findByNameStartingWithAndUserId(@Param("prefix") String prefix, @Param("userId") Long userId);

}
