package com.alielkhatib.surveyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponse {
    private Long id;
    private String title;
    private boolean closed;
    private String creatorUsername;
    private List<QuestionResponse> questions;
}