package com.mainproject.useraccount.controllers;


import com.mainproject.useraccount.configuration.JwtTokenUtil;
import com.mainproject.useraccount.entity.JwtRequest;
import com.mainproject.useraccount.entity.JwtResponse;
import com.mainproject.useraccount.entity.UserAuthentication;
import com.mainproject.useraccount.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        boolean login=authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
if(login) {
    final UserDetails userDetails = jwtUserDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));

}
else
{
    return new ResponseEntity<>("Invalid credentials",HttpStatus.OK);
}
    }
    private boolean authenticate(String username, String password) throws Exception {
        UserAuthentication user = this.otprepo.findByMailAddress(username).get(0);
        if (passwordEncoder.matches(password, user.getPassword()))
            return true;
        else
            return false;
    }

}