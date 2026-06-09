package com.alielkhatib.surveyapp.repository;

import com.alielkhatib.surveyapp.model.Question;
import com.alielkhatib.surveyapp.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByQuestion(Question question);
    List<Response> findByQuestionIn(List<Question> questions);
}