package com.stack_advisor.stack_advisor_backend.services;

import com.stack_advisor.stack_advisor_backend.models.Language;
import com.stack_advisor.stack_advisor_backend.models.enums.language.EntryThreshold;
import com.stack_advisor.stack_advisor_backend.models.enums.language.ExecutionModel;
import com.stack_advisor.stack_advisor_backend.models.enums.language.Popularity;
import com.stack_advisor.stack_advisor_backend.models.enums.language.Purpose;
import com.stack_advisor.stack_advisor_backend.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAllLanguages() { return languageRepository.findAll(); }

    public List<Language> getLanguagesByIdsList(List<Integer> ids) {
        ArrayList<Language> languages = new ArrayList<Language>();

        for (Integer id: ids) {
            languages.add(languageRepository.findById(id).get());
        }

        return languages;
    }

    public Language createLanguage(String name, EntryThreshold entryThreshold,  ExecutionModel executionModel, Popularity popularity, Purpose purpose) {
        Language language = new Language(
                name,
                entryThreshold,
                executionModel,
                popularity,
                purpose
        );

        language = languageRepository.save(language);
        return language;
    }
}
