package com.spring.cloud.com.rest;

import com.spring.cloud.com.domain.User;
import com.spring.cloud.com.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("user/{id}")
    public User findById(@PathVariable Long id){
        User one = userRepository.findOne(id);
        if(one!=null){
            return one;
        }else {
            return null;
        }
    }
}
