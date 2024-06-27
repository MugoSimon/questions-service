package com.mugosimon.question_service.dao;

import com.mugosimon.question_service.model.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends
        JpaRepository<Question, Integer> {

    @Query(value = "SELECT * FROM question WHERE difficulty_level = :difficultyLevel", nativeQuery = true)
    List<Question> findByDifficultyLevel(@Param("difficultyLevel") String difficultyLevel);

    @Query(value = "SELECT id FROM question WHERE category = :category ORDER BY RAND() LIMIT :numQuestions", nativeQuery = true)
    List<Integer> findRandomByCategory(@Param("category") String category, @Param("numQuestions") int numQuestions);

    @Query(value = "SELECT * FROM question WHERE question_title = :questionTitle", nativeQuery = true)
    Question findByQuestionTitle(@Param("questionTitle") String questionTitle);

    @Query(value = "SELECT * FROM question WHERE id = :id", nativeQuery = true)
    Question findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM question WHERE category = :category", nativeQuery = true)
    List<Question> findByCategory(@Param("category") String category);
}
