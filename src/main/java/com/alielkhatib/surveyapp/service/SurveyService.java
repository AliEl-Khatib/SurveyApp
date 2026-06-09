package com.alielkhatib.surveyapp.service;

import com.alielkhatib.surveyapp.dto.*;
import com.alielkhatib.surveyapp.model.*;
import com.alielkhatib.surveyapp.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final CurrentUserService currentUserService;

    public SurveyResponse createSurvey(SurveyRequest request) {
        User creator = currentUserService.getCurrentUser();

        Survey survey = Survey.builder()
                .title(request.getTitle())
                .creator(creator)
                .closed(false)
                .build();

        List<Question> questions = request.getQuestions().stream().map(q -> Question.builder()
                .text(q.getText())
                .type(q.getType())
                .minValue(q.getMinValue())
                .maxValue(q.getMaxValue())
                .options(q.getOptions())
                .survey(survey)
                .build()
        ).toList();

        survey.setQuestions(questions);
        Survey saved = surveyRepository.save(survey);
        return toResponse(saved);
    }

    public List<SurveyResponse> getMySurveys() {
        User user = currentUserService.getCurrentUser();
        return surveyRepository.findByCreator(user).stream().map(this::toResponse).toList();
    }

    public List<SurveyResponse> getOpenSurveys() {
        return surveyRepository.findByClosedFalse().stream().map(this::toResponse).toList();
    }

    public SurveyResponse getSurvey(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        return toResponse(survey);
    }

    public SurveyResponse closeSurvey(Long id) {
        User user = currentUserService.getCurrentUser();
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        if (!survey.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to close this survey");
        }

        survey.setClosed(true);
        return toResponse(surveyRepository.save(survey));
    }

    private SurveyResponse toResponse(Survey survey) {
        List<QuestionResponse> questions = survey.getQuestions().stream().map(q ->
                QuestionResponse.builder()
                        .id(q.getId())
                        .text(q.getText())
                        .type(q.getType())
                        .minValue(q.getMinValue())
                        .maxValue(q.getMaxValue())
                        .options(q.getOptions())
                        .build()
        ).toList();

        return SurveyResponse.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .closed(survey.isClosed())
                .creatorUsername(survey.getCreator().getUsername())
                .questions(questions)
                .build();
    }
}