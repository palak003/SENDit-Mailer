package com.mainproject.useraccount.services;

import java.util.ArrayList;
import java.util.List;


import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserAuthentication> userList=this.userAuthenticationRepository.findByMailAddress(username);
        if (!CollectionUtils.isEmpty(userList)) {
            UserAuthentication user = userList.get(0);
            return new User(user.getMailAddress(), user.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}