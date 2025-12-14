package com.stack_advisor.stack_advisor_backend.controllers;

import com.stack_advisor.stack_advisor_backend.models.Framework;
import com.stack_advisor.stack_advisor_backend.models.Language;
import com.stack_advisor.stack_advisor_backend.requests.FrameworkCreatingRequest;
import com.stack_advisor.stack_advisor_backend.responses.FrameworksListResponse;
import com.stack_advisor.stack_advisor_backend.responses.LanguagesListResponse;
import com.stack_advisor.stack_advisor_backend.services.FrameworkService;
import com.stack_advisor.stack_advisor_backend.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/framework/")
@CrossOrigin(origins = "*")
public class FrameworkController {
    @Autowired
    private FrameworkService frameworkService;

    @Autowired
    private LanguageService languageService;

    @GetMapping
    public ResponseEntity<?> getFrameworks() {
        List<Framework> frameworks = frameworkService.getAllFrameworks();

        ArrayList<FrameworksListResponse> response = new ArrayList<>();

        for (Framework framework : frameworks) {
            ArrayList<LanguagesListResponse> languagesResponse = new ArrayList<>();

            for (Language language : framework.getLanguages()) {
                languagesResponse.add(new LanguagesListResponse(
                        language.getId(),
                        language.getName(),
                        language.getEntryThreshold(),
                        language.getExecutionModel(),
                        language.getPopularity(),
                        language.getPurpose()
                ));
            }

            response.add(new FrameworksListResponse(
                    framework.getId(),
                    framework.getName(),
                    languagesResponse,
                    framework.getIsReactive(),
                    framework.getLastUpdatedAt(),
                    framework.getIsActual(),
                    framework.getTasksType()
            ));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createFramework(@RequestBody FrameworkCreatingRequest request) {

        Framework framework = frameworkService.createFramework(
                request.getName(),
                languageService.getLanguagesByIdsList(request.getLanguages()),
                request.getIsReactive(),
                request.getLastUpdatedAt(),
                request.getTasksType()
        );

        return ResponseEntity.ok(framework);
    }
}
