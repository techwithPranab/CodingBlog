package com.blog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.bo.ErrorBO;
import com.blog.bo.UserResponseBO;
import com.blog.model.User;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // Create a logger instance
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
 
    @Autowired
    private UserRepository userRepository;

     @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostMapping
    public UserResponseBO createUser(@RequestBody User user){

        User dbUser = userRepository.findByUsername(user.getUsername());
        UserResponseBO responseBO = new UserResponseBO();
        if(dbUser != null)
        {
            responseBO.getErrorBO().setErrorDesc("Username already exist");
        }
        else
        {
            dbUser = userRepository.save(user);
        }
        responseBO.setUser(dbUser);
        return responseBO;
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/authenticate")
    public UserResponseBO authenticateUser(@RequestBody User user){
        String userToken = null;
        UserResponseBO userResponseBO = new UserResponseBO();
        ErrorBO errorBO = new ErrorBO();
        User dbUser = userRepository.findByUsername(user.getUsername());
        if(dbUser != null){
            if(dbUser.getPassword().equals(user.getPassword())){
                    userToken = jwtTokenUtil.generateToken(dbUser.getUsername());
                    userResponseBO.setUserToken(userToken);
                }
            else{
                errorBO.setErrorDesc("User login password is incorrect");
                logger.debug("User login password is incorrect");
            }
            userResponseBO.setUser(dbUser);
        }
        else{
            errorBO.setErrorDesc("User does not exist");
            logger.debug("User does not exist");
        }
        userResponseBO.setErrorBO(errorBO);
        
        return userResponseBO;
    }
}
