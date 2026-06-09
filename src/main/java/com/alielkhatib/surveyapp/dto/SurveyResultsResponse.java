package com.alielkhatib.surveyapp.dto;

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
public class SurveyResultsResponse {
    private Long surveyId;
    private String title;
    private boolean closed;
    private List<QuestionResultResponse> questionResults;
}