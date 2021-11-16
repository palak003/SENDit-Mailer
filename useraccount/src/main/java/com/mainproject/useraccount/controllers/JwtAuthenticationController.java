package com.mainproject.useraccount.controllers;


import com.mainproject.useraccount.configuration.JwtTokenUtil;
import com.mainproject.useraccount.entity.JwtRequest;
import com.mainproject.useraccount.entity.JwtResponse;
import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins= "*")
public class JwtAuthenticationController {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthenticationRepository otprepo;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        int login=authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
if(login==1) {
    final UserDetails userDetails = jwtUserDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));

}
else if(login==2)
{
    return new ResponseEntity<>("Invalid credentials",HttpStatus.OK);
}
else if(login==0) {
    return new ResponseEntity<>("You don't have any account please signUp first", HttpStatus.OK);
}
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    private int authenticate(String username, String password) throws Exception {


        if(CollectionUtils.isEmpty(this.otprepo.findByMailAddress(username)))
            return 0;
        else {
            UserAuthentication user = this.otprepo.findByMailAddress(username).get(0);
            if (passwordEncoder.matches(password, user.getPassword()))
                return 1;
            else
                return 2;
        }
    }

}