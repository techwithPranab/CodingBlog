package com.blog.controller;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    // Get all questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Post a new question
    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    // Get a question by ID
    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable String id) {
        return questionRepository.findById(id).orElse(null);
    }

    // Add an answer to a question
    @PostMapping("/{id}/answers")
    public Question addAnswer(@PathVariable String id, @RequestBody Answer answer) {
        Question question = questionRepository.findById(id).orElse(null);
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
                
            return questionRepository.save(question);
        }
        return null;
    }

    // Upvote or downvote an answer
    @PostMapping("/{id}/answers/{answerId}/vote")
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
}
