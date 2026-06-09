package com.alielkhatib.surveyapp.repository;

import com.alielkhatib.surveyapp.model.Survey;
import com.alielkhatib.surveyapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByCreator(User creator);
    List<Survey> findByClosedFalse();
}