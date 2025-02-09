package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.model.User;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {
 
    @Autowired
    private UserRepository userRepository;

     @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody User user){

        User dbUser = userRepository.findByUsername(user.getUsername());
        String userToken = null;
        if(dbUser != null){
            userToken = jwtTokenUtil.generateToken(dbUser.getUsername());
        }
        return userToken;
    }
}
