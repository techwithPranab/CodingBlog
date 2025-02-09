package com.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.model.Tags;

public interface TagsRepository extends MongoRepository<Tags, String> {
    
}
