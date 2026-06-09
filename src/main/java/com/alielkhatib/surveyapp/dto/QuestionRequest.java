package com.alielkhatib.surveyapp.dto;

import com.alielkhatib.surveyapp.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class QuestionRequest {
    @NotBlank
    private String text;

    @NotNull
    private QuestionType type;

    private Integer minValue;
    private Integer maxValue;
    private List<String> options;
}