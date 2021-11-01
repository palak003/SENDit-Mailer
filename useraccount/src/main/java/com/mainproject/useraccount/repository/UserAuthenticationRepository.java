package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication,Integer> {

    List<UserAuthentication> findByMailAddress(String mailAddress);
}
