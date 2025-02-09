package com.blog;

import java.io.IOException;

import com.blog.model.Answer;
import com.blog.model.Question;
import com.blog.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenerateJSON {
    public static void main(String[] args) {
            Question question = new Question();
            question.setTitle("How to create Spring Boot application");
            question.setAuthor("Pranab Paul");
            question.setDescription("I want to understand how to create spring boot application");

            Answer answer = new Answer();
            answer.setAuthor("Pranab Paul");
            answer.setContent("You can create a spring boot object by followign way");
            answer.setVotes(1);

            User user  =new User();
            user.setFirstName("Pranab");
            user.setLastName("Paul");
            user.setEmail("pranabpiitk2024@gmail.com");
            user.setUsername("pranabpaul01");
            user.setPassword("Kolkata@84");
            user.setActiveInd("Y");

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String jsonString = objectMapper.writeValueAsString(question);
                System.out.println(jsonString);
                jsonString = objectMapper.writeValueAsString(answer);
                System.out.println(jsonString);

                jsonString = objectMapper.writeValueAsString(user);
                System.out.println(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
