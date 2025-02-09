package com.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
}
