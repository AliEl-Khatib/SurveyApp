package com.alielkhatib.surveyapp.dto;

import com.alielkhatib.surveyapp.model.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private Long id;
    private String text;
    private QuestionType type;
    private Integer minValue;
    private Integer maxValue;
    private List<String> options;
}