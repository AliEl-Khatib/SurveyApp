package com.alielkhatib.surveyapp.controller;

import com.alielkhatib.surveyapp.dto.SurveyRequest;
import com.alielkhatib.surveyapp.dto.SurveyResponse;
import com.alielkhatib.surveyapp.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<SurveyResponse> createSurvey(@Valid @RequestBody SurveyRequest request) {
        return ResponseEntity.ok(surveyService.createSurvey(request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<SurveyResponse>> getMySurveys() {
        return ResponseEntity.ok(surveyService.getMySurveys());
    }

    @GetMapping("/open")
    public ResponseEntity<List<SurveyResponse>> getOpenSurveys() {
        return ResponseEntity.ok(surveyService.getOpenSurveys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponse> getSurvey(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getSurvey(id));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<SurveyResponse> closeSurvey(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.closeSurvey(id));
    }
}