package com.alielkhatib.surveyapp.service;

import com.alielkhatib.surveyapp.dto.*;
import com.alielkhatib.surveyapp.model.*;
import com.alielkhatib.surveyapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;
    private final CurrentUserService currentUserService;

    public void submitResponses(Long surveyId, List<SubmitResponseRequest> requests) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        if (survey.isClosed()) {
            throw new RuntimeException("Survey is closed");
        }

        User respondent = currentUserService.getCurrentUser();

        List<Response> responses = requests.stream().map(req -> {
            Question question = questionRepository.findById(req.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found: " + req.getQuestionId()));

            validateResponse(question, req.getValue());

            return Response.builder()
                    .question(question)
                    .value(req.getValue())
                    .respondent(respondent)
                    .build();
        }).toList();

        responseRepository.saveAll(responses);
    }

    public SurveyResultsResponse getResults(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        User user = currentUserService.getCurrentUser();
        if (!survey.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to view results");
        }

        List<QuestionResultResponse> questionResults = survey.getQuestions().stream().map(question -> {
            List<Response> responses = responseRepository.findByQuestion(question);
            return buildQuestionResult(question, responses);
        }).toList();

        return SurveyResultsResponse.builder()
                .surveyId(survey.getId())
                .title(survey.getTitle())
                .closed(survey.isClosed())
                .questionResults(questionResults)
                .build();
    }

    private QuestionResultResponse buildQuestionResult(Question question, List<Response> responses) {
        QuestionResultResponse.QuestionResultResponseBuilder builder = QuestionResultResponse.builder()
                .questionId(question.getId())
                .text(question.getText())
                .type(question.getType());

        switch (question.getType()) {
            case OPEN_ENDED -> builder.openEndedAnswers(
                    responses.stream().map(Response::getValue).toList()
            );
            case NUMBER_RANGE -> {
                List<Integer> values = responses.stream()
                        .map(r -> Integer.parseInt(r.getValue()))
                        .toList();
                double avg = values.stream().mapToInt(Integer::intValue).average().orElse(0);
                builder.numberValues(values).average(avg);
            }
            case MCQ -> {
                Map<String, Long> counts = responses.stream()
                        .collect(Collectors.groupingBy(Response::getValue, Collectors.counting()));
                // ensure all options appear even with 0 votes
                question.getOptions().forEach(opt -> counts.putIfAbsent(opt, 0L));
                builder.optionCounts(counts);
            }
        }

        return builder.build();
    }

    private void validateResponse(Question question, String value) {
        switch (question.getType()) {
            case NUMBER_RANGE -> {
                try {
                    int num = Integer.parseInt(value);
                    if (num < question.getMinValue() || num > question.getMaxValue()) {
                        throw new RuntimeException("Value out of range: " + question.getMinValue() + "-" + question.getMaxValue());
                    }
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Value must be a number");
                }
            }
            case MCQ -> {
                if (!question.getOptions().contains(value)) {
                    throw new RuntimeException("Invalid option: " + value);
                }
            }
        }
    }
}