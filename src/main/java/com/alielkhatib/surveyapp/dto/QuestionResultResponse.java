package com.alielkhatib.surveyapp.dto;

import com.alielkhatib.surveyapp.model.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultResponse {
    private Long questionId;
    private String text;
    private QuestionType type;

    // OPEN_ENDED: list of text answers
    private List<String> openEndedAnswers;

    // NUMBER_RANGE: average + all values
    private Double average;
    private List<Integer> numberValues;

    // MCQ: count per option
    private Map<String, Long> optionCounts;
}