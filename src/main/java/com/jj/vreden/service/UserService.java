package com.jj.vreden.service;

import com.jj.vreden.configuration.AuthenticationFacade;
import com.jj.vreden.model.data.User;
import com.jj.vreden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class UserService {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;

    public User getUser() {
        return userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
    }

}
