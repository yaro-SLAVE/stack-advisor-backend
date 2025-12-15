package com.stack_advisor.stack_advisor_backend.controllers;

import com.stack_advisor.stack_advisor_backend.drools.services.StackAdvisorService;
import com.stack_advisor.stack_advisor_backend.requests.ProjectRequirementsRequest;
import com.stack_advisor.stack_advisor_backend.responses.ProjectRecommendedResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/recommended")
@CrossOrigin(origins = "*")
public class StackAdvisorController {
    @Autowired
    private StackAdvisorService stackAdvisorService;

    @PostMapping
    public ResponseEntity<ProjectRecommendedResponse> getRecommendations(
            @Valid @RequestBody ProjectRequirementsRequest request) {

        ProjectRecommendedResponse response =
                stackAdvisorService.getRecommendations(request);

        return ResponseEntity.ok(response);
    }


}