package com.mainproject.useraccount.repository;

import com.mainproject.useraccount.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication,Integer> {

    Optional<UserAuthentication> findByUsername(String userName);
}
