package com.alielkhatib.surveyapp.repository;

import com.alielkhatib.surveyapp.model.Question;
import com.alielkhatib.surveyapp.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySurvey(Survey survey);
}