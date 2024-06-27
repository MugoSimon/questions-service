package com.mugosimon.question_service.service;

import com.mugosimon.question_service.dao.QuestionDao;
import com.mugosimon.question_service.model.Question;
import com.mugosimon.question_service.model.QuestionWrapper;
import com.mugosimon.question_service.model.Response;
import com.mugosimon.question_service.utils.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuestionService {

    private final String TAG = "QuestionService: ";

    @Autowired
    private QuestionDao questionDao;

    public EntityResponse getAllQuestions() {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> fetch all questions <====");
            if (questionDao.count() == 0) {
                log.info(TAG + "No questions found");
                entityResponse.setEntity(null);
                entityResponse.setMessage("No questions found");
                entityResponse.setStatusCode(404);
            } else {
                log.info(TAG + "Questions fetched successfully: " + questionDao.count() + " records found");
                entityResponse.setEntity(questionDao.findAll());
                entityResponse.setMessage("Questions fetched successfully");
                entityResponse.setStatusCode(200);
            }
        } catch (Exception e) {
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
        }
        return entityResponse;
    }

    public EntityResponse fetchQuestionByDifficultyLevel(String difficultyLevel) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> fetch questions by difficulty level <====");
            List<Question> questionsList = questionDao.findByDifficultyLevel(difficultyLevel);
            if (questionsList.isEmpty()) {
                log.info(TAG + "No questions found");
                entityResponse.setEntity(null);
                entityResponse.setMessage("No questions found");
                entityResponse.setStatusCode(404);
            } else {
                log.info(TAG + "Questions fetched successfully: " + questionsList.stream().count() + " records found");
                entityResponse.setEntity(questionsList);
                entityResponse.setMessage("Questions fetched successfully: " + questionsList.stream().count() + " records found");
                entityResponse.setStatusCode(200);
            }
        } catch (Exception e) {
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
        }
        return entityResponse;
    }

    public EntityResponse addQuestion(Question question) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> add question <====");
            Question questions = questionDao.findByQuestionTitle(question.getQuestionTitle());
            if (questions != null) {
                log.info(TAG + "Question already exists");
                entityResponse.setEntity(null);
                entityResponse.setMessage("Question already exists");
                entityResponse.setStatusCode(409);
                return entityResponse;
            }
            entityResponse.setEntity(questionDao.save(question));
            entityResponse.setMessage("Question added successfully");
            log.info(TAG + "Question added successfully");
            entityResponse.setStatusCode(200);
        } catch (Exception e) {
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
        }
        return entityResponse;
    }

    public EntityResponse fetchQuestionByCategory(String category) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> fetch questions by category <====");
            List<Question> questionsList = questionDao.findByCategory(category);
            if (questionsList.isEmpty()) {
                log.info(TAG + "No questions found");
                entityResponse.setEntity(null);
                entityResponse.setMessage("No questions found");
                entityResponse.setStatusCode(404);
            } else {
                log.info(TAG + "Questions fetched successfully: " + questionsList.stream().count() + " records found");
                entityResponse.setEntity(questionsList);
                entityResponse.setMessage("Questions fetched successfully: " + questionsList.stream().count() + " records found");
                entityResponse.setStatusCode(200);
            }
        } catch (Exception e) {
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
        }
        return entityResponse;
    }

    public EntityResponse updateQuestion(Question question) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> update question <====");
            Question questions = questionDao.findByQuestionTitle(question.getQuestionTitle());
            if (questions == null) {
                log.info(TAG + "Question not found");
                entityResponse.setEntity(null);
                entityResponse.setMessage("Question not found");
                entityResponse.setStatusCode(404);
                return entityResponse;
            }
            entityResponse.setEntity(questionDao.save(question));
            entityResponse.setMessage("Question updated successfully");
            log.info(TAG + "Question updated successfully");
            entityResponse.setStatusCode(200);
        } catch (Exception e) {
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
        }
        return entityResponse;
    }

    public EntityResponse deleteQuestion(Integer questionTitle) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> delete question <====");
            Optional<Question> question = questionDao.findById(questionTitle);
            if (!question.isPresent()) {
                log.info(TAG + "Question not found");
                entityResponse.setEntity(null);
                entityResponse.setMessage("Question not found");
                entityResponse.setStatusCode(404);
                return entityResponse;
            }
            questionDao.delete(question.get());
            entityResponse.setEntity(null);
            entityResponse.setMessage("Question deleted successfully");
            log.info(TAG + "Question deleted successfully");
            entityResponse.setStatusCode(200);
        } catch (Exception e) {
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
        }
        return entityResponse;
    }

    public EntityResponse<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        EntityResponse<List<Integer>> entityResponse = new EntityResponse<>();
        try {
            log.info("====> get questions for quiz <====");

            // Fetch random question IDs
            List<Integer> questionIds = questionDao.findRandomByCategory(categoryName, numQuestions);

            if (questionIds.isEmpty()) {
                log.info("No questions found");
                entityResponse.setEntity(null);
                entityResponse.setMessage("No questions found");
                entityResponse.setStatusCode(404);
            } else {
                log.info("Questions fetched successfully: " + questionIds.size() + " records found");
                entityResponse.setEntity(questionIds);
                entityResponse.setMessage("Questions fetched successfully: " + questionIds.size() + " records found");
                entityResponse.setStatusCode(200);
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching questions for quiz", e);
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
        }
        return entityResponse;
    }

    public EntityResponse fetchQuestionsById(List<Integer> questionsID) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> Fetch questions by ID <====");
            List<QuestionWrapper> wrappers = new ArrayList<>();
            List<Question> questions = new ArrayList<>();

            for (Integer id : questionsID) {
                Optional<Question> questionOpt = questionDao.findById(id);
                if (questionOpt.isPresent()) {
                    questions.add(questionOpt.get());
                } else {
                    log.warn("Question with ID " + id + " not found");
                }
            }

            for (Question question : questions) {
                QuestionWrapper wrapper = new QuestionWrapper();
                wrapper.setId(question.getId());
                wrapper.setQuestionTitle(question.getQuestionTitle());
                wrapper.setOption1(question.getOption1());
                wrapper.setOption2(question.getOption2());
                wrapper.setOption3(question.getOption3());
                wrapper.setOption4(question.getOption4());

                wrappers.add(wrapper);
            }

            if (wrappers.isEmpty()) {
                entityResponse.setEntity(null);
                entityResponse.setMessage("No questions found for the provided IDs");
                entityResponse.setStatusCode(404);
            } else {
                entityResponse.setEntity(wrappers);
                entityResponse.setMessage("Questions fetched successfully");
                entityResponse.setStatusCode(200);
            }
        } catch (Exception e) {
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
            log.error("Error occurred while fetching questions by ID: ", e);
        }
        return entityResponse;
    }

    public EntityResponse fetchScore(List<Response> responses) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> Fetch score <====");
            int right = 0;
            for (Response response : responses) {
                Question question = questionDao.findById(response.getId()).orElse(null);
                if (question != null) {
                    log.info("Question " + question.getId() + ". " + question.getQuestionTitle());
                    log.info("Response: " + response.getResponse() + ", Right answer: " + question.getRightAnswer());
                    // Trim and compare to ensure there are no leading/trailing spaces
                    if (response.getResponse().trim().equals(question.getRightAnswer().trim())) {
                        log.info("Correct answer for question ID: " + response.getId());
                        right++;
                    } else {
                        log.info("Incorrect answer for question ID: " + response.getId());
                    }
                } else {
                    log.warn("Question not found for ID: " + response.getId());
                }
            }
            log.info("Score fetched successfully: " + right);
            entityResponse.setEntity(right);
            entityResponse.setMessage("Score fetched successfully");
            entityResponse.setStatusCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            entityResponse.setEntity(null);
            entityResponse.setMessage("Error occurred: " + e.getMessage());
            entityResponse.setStatusCode(500);
            log.error("Error occurred while fetching score", e);
        }
        return entityResponse;
    }

}
