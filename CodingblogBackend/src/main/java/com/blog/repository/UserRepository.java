package com.blog.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    User findByUsername(String username);
    User findByEmail(String email);
}
