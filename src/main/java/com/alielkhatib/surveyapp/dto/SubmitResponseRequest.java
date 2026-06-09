package com.alielkhatib.surveyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitResponseRequest {
    @NotNull
    private Long questionId;

    @NotBlank
    private String value;
}