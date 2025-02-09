package com.blog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.blog.model.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {

   @Query("{ 'title' : { $regex: ?0, $options: 'i' } }") // Case-insensitive search
    List<Question> findByTitleLike(String namePattern);

    List<Question> findByAuthor(String author);

    @Query("{ 'answers.author' : ?0 }")
    List<Question> findQuestionByAnswerd(String author);

}
