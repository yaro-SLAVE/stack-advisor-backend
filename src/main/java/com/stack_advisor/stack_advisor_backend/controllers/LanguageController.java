package com.stack_advisor.stack_advisor_backend.controllers;

import com.stack_advisor.stack_advisor_backend.models.Language;
import com.stack_advisor.stack_advisor_backend.requests.LanguageCreatingRequest;
import com.stack_advisor.stack_advisor_backend.responses.LanguagesListResponse;
import com.stack_advisor.stack_advisor_backend.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/language")
@CrossOrigin(origins = "*")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @GetMapping
    public ResponseEntity<?> getLanguagesList() {
        List<Language> languages = languageService.getAllLanguages();

        ArrayList<LanguagesListResponse> response = new ArrayList<>();

        for (Language language : languages) {
            response.add(new LanguagesListResponse(
                    language.getId(),
                    language.getName(),
                    language.getEntryThreshold(),
                    language.getExecutionModel(),
                    language.getPopularity(),
                    language.getPurpose()
            ));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createLanguage(@RequestBody LanguageCreatingRequest request) {
        Language language = languageService.createLanguage(
                request.getName(),
                request.getEntryThreshold(),
                request.getExecutionModel(),
                request.getPopularity(),
                request.getPurpose()
        );

        return ResponseEntity.ok(new LanguagesListResponse(language.getId(), language.getName(), language.getEntryThreshold(), language.getExecutionModel(), language.getPopularity(), language.getPurpose()));
    }

}
