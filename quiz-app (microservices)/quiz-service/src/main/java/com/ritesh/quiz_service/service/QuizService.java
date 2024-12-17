package com.ritesh.quiz_service.service;


import com.ritesh.quiz_service.dao.QuizDao;
import com.ritesh.quiz_service.feign.QuizInterface;
import com.ritesh.quiz_service.model.Question;
import com.ritesh.quiz_service.model.QuestionWrapper;
import com.ritesh.quiz_service.model.Quiz;
import com.ritesh.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;


    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

       // here we need to communicate with question service using openFeign and eureka client

        List<Integer> questionIDs = quizInterface.generateQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIDs(questionIDs);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestions(quiz.getQuestionIDs());
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> result = quizInterface.getScore(responses);
        return result;
    }
}
