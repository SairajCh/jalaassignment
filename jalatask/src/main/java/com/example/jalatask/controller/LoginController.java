package com.example.jalatask.controller;

import com.example.jalatask.model.User;
import com.example.jalatask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user")
    public User getUserDetailsAfterLogin(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (user!=null) {
            return user;
        } else {
            return null;
        }

    }


}
