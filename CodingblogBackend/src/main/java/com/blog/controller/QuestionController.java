package com.blog.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.model.Answer;
import com.blog.model.Question;
import com.blog.repository.QuestionRepository;

@RestController
@RequestMapping("/api")
public class QuestionController {

    // Create a logger instance
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    // Get all questions
    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        logger.debug("Inside getAllQuestion menthod");
        return questionRepository.findAll();
    }

    // Post a new question
    @PostMapping("/questions")
    public Question createQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    // Get a question by ID
    @GetMapping("/questions/{id}")
    public Question getQuestion(@PathVariable String id) {
        Question responseQues = questionRepository.findById(id).orElse(null);
        if(null != responseQues)
        {
            int currentViewCount = responseQues.getViewCount();
            currentViewCount ++;
            responseQues.setViewCount(currentViewCount);
            questionRepository.save(responseQues);
        }
        return responseQues;
    }

    // Add an answer to a question
    @PostMapping("/questions/{id}/answers")
    public Question addAnswer(@PathVariable String id, @RequestBody Answer answer) {
        Question question = questionRepository.findById(id).orElse(null);
        String customId = UUID.randomUUID().toString();
        answer.setAnswerId(customId);
        if (question != null) {
            if(question.getAnswers() != null)
            {
                question.getAnswers().add(answer);
            }
            else{
                List<Answer> ansList = new ArrayList<>();
                ansList.add(answer);
                question.setAnswers(ansList);
            }
            Date currDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            question.setLastUpdatedDate(formatter.format(currDate));
            return questionRepository.save(question);
        }
        return null;
    }

    // Upvote or downvote an answer
    @PostMapping("/questions/{id}/answers/{answerId}/vote")
    public Question voteAnswer(@PathVariable String id, @PathVariable String answerId, @RequestParam boolean upvote) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question != null) {
            for (Answer answer : question.getAnswers()) {
                if (answer.getAnswerId().equals(answerId)) {
                    answer.setVotes(answer.getVotes() + (upvote ? 1 : -1));
                    break;
                }
            }
            return questionRepository.save(question);
        }
        return null;
    }

    @GetMapping("/questionByTitle")
    public List<Question> findQuestionByDesc(@RequestParam String desc){
        System.out.println("Inside method "+desc);
        return questionRepository.findByTitleLike(desc);
    }

    @GetMapping("/questionByAuthor")
    public List<Question> findQuestionByAuthor(@RequestParam String author){
        return questionRepository.findByAuthor(author);
    }

    @GetMapping("/answeredByAuthor")
    public List<Question> findQuestionByAnswered(@RequestParam String author){
        return questionRepository.findQuestionByAnswerd(author);
    }
}
