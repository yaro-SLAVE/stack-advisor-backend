package com.stack_advisor.stack_advisor_backend.drools.services;

import com.stack_advisor.stack_advisor_backend.drools.data.DataStorageRecommended;
import com.stack_advisor.stack_advisor_backend.drools.data.FrameworkRecommended;
import com.stack_advisor.stack_advisor_backend.drools.data.LanguageRecommended;
import com.stack_advisor.stack_advisor_backend.models.DataStorage;
import com.stack_advisor.stack_advisor_backend.models.Framework;
import com.stack_advisor.stack_advisor_backend.models.Language;
import com.stack_advisor.stack_advisor_backend.repositories.DataStorageRepository;
import com.stack_advisor.stack_advisor_backend.repositories.FrameworkRepository;
import com.stack_advisor.stack_advisor_backend.repositories.LanguageRepository;
import com.stack_advisor.stack_advisor_backend.requests.ProjectRequirementsRequest;
import com.stack_advisor.stack_advisor_backend.responses.ProjectRecommendedResponse;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StackAdvisorService {
    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private FrameworkRepository frameworkRepository;

    @Autowired
    private DataStorageRepository dataStorageRepository;

    @Autowired
    private KieContainer kieContainer;

    public ProjectRecommendedResponse getRecommendations(ProjectRequirementsRequest request) {
        List<Language> allLanguages = languageRepository.findAll();
        List<Framework> allFrameworks = frameworkRepository.findAll();
        List<DataStorage> allDataStorages = dataStorageRepository.findAll();

        KieSession kieSession = kieContainer.newKieSession();

        List<LanguageRecommended> languageResults = new ArrayList<>();
        List<FrameworkRecommended> frameworkResults = new ArrayList<>();
        List<DataStorageRecommended> dataStorageResults = new ArrayList<>();

        kieSession.insert(request);

        for (Language language : allLanguages) {
            LanguageRecommended result = new LanguageRecommended(language, 0.0);
            languageResults.add(result);
            kieSession.insert(result);
        }

        for (Framework framework : allFrameworks) {
            FrameworkRecommended result = new FrameworkRecommended(framework, 0.0);
            frameworkResults.add(result);
            kieSession.insert(result);
        }

        for (DataStorage dataStorage : allDataStorages) {
            DataStorageRecommended result = new DataStorageRecommended(dataStorage, 0.0);
            dataStorageResults.add(result);
            kieSession.insert(result);
        }

        kieSession.fireAllRules();
        kieSession.dispose();

        List<Language> recommendedLanguages = languageResults.stream()
                .filter(r -> r.getScore() > 0.5)
                .sorted(Comparator.comparing(LanguageRecommended::getScore).reversed())
                .map(LanguageRecommended::getLanguage)
                .collect(Collectors.toList());

        List<Framework> recommendedFrameworks = frameworkResults.stream()
                .filter(r -> r.getScore() > 0.5)
                .sorted(Comparator.comparing(FrameworkRecommended::getScore).reversed())
                .map(FrameworkRecommended::getFramework)
                .collect(Collectors.toList());

        List<DataStorage> recommendedDataStorages = dataStorageResults.stream()
                .filter(r -> r.getScore() > 0.5)
                .sorted(Comparator.comparing(DataStorageRecommended::getScore).reversed())
                .map(DataStorageRecommended::getDataStorage)
                .collect(Collectors.toList());

        ProjectRecommendedResponse response = new ProjectRecommendedResponse();

        response.setLanguageRecommendedList(recommendedLanguages);
        response.setFrameworkRecommendedList(recommendedFrameworks);
        response.setDataStorageRecommendedList(recommendedDataStorages);

        return response;
    }
}
