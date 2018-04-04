package com.spring.cloud.com.rest;

import com.spring.cloud.com.domain.User;
import com.spring.cloud.com.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository userRepository;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "user/{id}", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public User findById(@PathVariable Long id) {
        User one = userRepository.findOne(id);
        if (one != null) {
            return one;
        } else {
            return null;
        }
    }

    @GetMapping(value = "log/user/{id}")
    public User findLogById(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            String auth_str = StringUtils.join(user.getAuthorities(), ",");
            LOGGER.info("当前用户是{}，角色是{}", user.getUsername(),  auth_str);
        }
        User one = userRepository.findOne(id);
        return one;
    }

}
