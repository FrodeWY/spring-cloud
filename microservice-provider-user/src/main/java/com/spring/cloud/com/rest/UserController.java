package com.spring.cloud.com.rest;

import com.spring.cloud.com.domain.User;
import com.spring.cloud.com.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "user/{id}",produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    public User findById(@PathVariable Long id){
        User one = userRepository.findOne(id);
        if(one!=null){
            return one;
        }else {
            return null;
        }
    }

    @GetMapping(value = "user-get",produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    public User getUser(@RequestParam Long id,@RequestParam String username){
        User one = userRepository.findOne(id);
        if(one!=null){
            return one;
        }else {
            return null;
        }
    }
    @GetMapping(value = "admin/test",produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    public User adminTest(@RequestParam Long id,@RequestParam String username){
        User one = userRepository.findOne(id);
        if(one!=null){
            return one;
        }else {
            return null;
        }
    }
    @PostMapping(value = "user-post",produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    public User getUser(@RequestBody User user){
        User one = userRepository.findOne(user.getId());
        if(one!=null){
            return one;
        }else {
            return null;
        }
    }
}
