package com.mugosimon.question_service.controller;

import com.mugosimon.question_service.model.Question;
import com.mugosimon.question_service.model.Response;
import com.mugosimon.question_service.service.QuestionService;
import com.mugosimon.question_service.utils.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;


    @RequestMapping("/allQuestions")
    public EntityResponse getAllQuestions() {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getAllQuestions();
    }

    @RequestMapping("/difficulty")
    public EntityResponse fetchQuestionByDifficultyLevel(@RequestParam String difficultyLevel) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.fetchQuestionByDifficultyLevel(difficultyLevel);
    }

    @PostMapping("/add")
    public EntityResponse addQuestion(@RequestBody Question question) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/delete")
    public EntityResponse deleteQuestion(@RequestParam Integer id) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.deleteQuestion(id);
    }

    @GetMapping("/generate")
    public EntityResponse<List<Integer>> getQuestionForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("/fetchQuestions")
    public EntityResponse fetchQuestionsById(@RequestBody List<Integer> questionsID) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.fetchQuestionsById(questionsID);
    }

    @PostMapping("/getScore")
    public EntityResponse fetchScore(@RequestBody List<Response> responses) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.fetchScore(responses);
    }
}
