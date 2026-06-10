package com.alielkhatib.surveyapp.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("GET works");
    }

    @PostMapping
    public ResponseEntity<String> post() {
        return ResponseEntity.ok("POST works");
    }
}