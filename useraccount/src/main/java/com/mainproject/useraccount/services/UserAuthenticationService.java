package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    List<UserAuthentication> list;

    public UserAuthenticationService()
    {
        list=new ArrayList<>();
    }

    public List<UserAuthentication> getAll() {
        return this.userAuthenticationRepository.findAll();

    }

    public Optional<UserAuthentication> getOne(int personId) {
        return this.userAuthenticationRepository.findById(personId);
    }

    public void createAccount(UserAuthentication details) {
        this.userAuthenticationRepository.save(details);
    }
}
