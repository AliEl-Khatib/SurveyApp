package com.alielkhatib.surveyapp.controller;

import com.alielkhatib.surveyapp.dto.SubmitResponseRequest;
import com.alielkhatib.surveyapp.dto.SurveyResultsResponse;
import com.alielkhatib.surveyapp.service.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;

    @PostMapping("/{surveyId}/respond")
    public ResponseEntity<Void> submitResponses(
            @PathVariable Long surveyId,
            @Valid @RequestBody List<SubmitResponseRequest> requests) {
        responseService.submitResponses(surveyId, requests);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{surveyId}/results")
    public ResponseEntity<SurveyResultsResponse> getResults(@PathVariable Long surveyId) {
        return ResponseEntity.ok(responseService.getResults(surveyId));
    }
}