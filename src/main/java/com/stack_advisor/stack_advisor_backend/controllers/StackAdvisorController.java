package com.stack_advisor.stack_advisor_backend.controllers;

import com.stack_advisor.stack_advisor_backend.drools.services.StackAdvisorService;
import com.stack_advisor.stack_advisor_backend.models.ProjectRequirements;
import com.stack_advisor.stack_advisor_backend.models.RecommendedStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/stack-advisor")
@CrossOrigin(origins = "*")
public class StackAdvisorController {
    @Autowired
    private StackAdvisorService stackAdvisorService;

    @PostMapping("/recommend")
    public RecommendedStack recommendStack(@RequestBody ProjectRequirements requirements) {
        return stackAdvisorService.recommendStack(requirements);
    }

    @GetMapping("/health")
    public String health() {
        return "Tech Stack Advisor is running!";
    }
}