package com.mainproject.useraccount.services;

import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremiumService {
    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    public void premium(String username) {
        List<UserAuthentication> userList = this.userAuthenticationRepository.findByMailAddress(username);
        UserAuthentication user = userList.get(0);
        user.setPremium(true);
        this.userAuthenticationRepository.save(user);
    }

    public String check(String username) {
        List<UserAuthentication> userList = this.userAuthenticationRepository.findByMailAddress(username);
        UserAuthentication user = userList.get(0);
        if(user.isPremium())
            return "premium";
        else
            return "non-premium";

    }

}
