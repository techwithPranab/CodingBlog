package com.blog.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.blog.model.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {

    static final Logger logger = LoggerFactory.getLogger(QuestionRepository.class);

   @Query("{ 'title' : { $regex: ?0, $options: 'i' } }") // Case-insensitive search
    List<Question> findByTitleLike(String namePattern);

    List<Question> findByAuthor(String author,Sort sort);

    @Query("{ 'answers.author' : ?0 }")
    List<Question> findQuestionByAnswerd(String author,Sort sort);

}
