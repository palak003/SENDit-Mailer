package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication,Integer> {
}
