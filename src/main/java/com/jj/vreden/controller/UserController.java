package com.jj.vreden.controller;

import com.jj.vreden.model.response.JwtResponse;
import com.jj.vreden.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public JwtResponse login(@RequestParam  String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String jwt = JWTUtil.generateJwtToken(authentication.getName(), null, 60*60*24);
        return new JwtResponse(jwt);
    }

}
