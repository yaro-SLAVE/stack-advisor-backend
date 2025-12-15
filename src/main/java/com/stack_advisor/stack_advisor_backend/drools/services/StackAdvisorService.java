package com.stack_advisor.stack_advisor_backend.drools.services;

import com.stack_advisor.stack_advisor_backend.drools.components.CustomAgendaEventListener;
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
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StackAdvisorService {
    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private FrameworkRepository frameworkRepository;

    @Autowired
    private DataStorageRepository dataStorageRepository;

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private CustomAgendaEventListener agendaEventListener;

    @Autowired
    private ExplanationService explanationService;

    public ProjectRecommendedResponse getRecommendations(ProjectRequirementsRequest request) {
        List<Language> allLanguages = languageRepository.findAll();
        List<Framework> allFrameworks = frameworkRepository.findAll();
        List<DataStorage> allDataStorages = dataStorageRepository.findAll();

        KieSession kieSession = kieContainer.newKieSession();
        Long sessionId = kieSession.getIdentifier();

        // Регистрируем слушатель событий
        kieSession.addEventListener(agendaEventListener);

        List<LanguageRecommended> languageResults = new ArrayList<>();
        List<FrameworkRecommended> frameworkResults = new ArrayList<>();
        List<DataStorageRecommended> dataStorageResults = new ArrayList<>();

        // Вставляем запрос
        kieSession.insert(request);
        log.debug("Inserted ProjectRequirementsRequest into session {}", sessionId);

        // Вставляем языки
        for (Language language : allLanguages) {
            LanguageRecommended result = new LanguageRecommended(language, 0.0);
            languageResults.add(result);
            kieSession.insert(result);
        }
        log.debug("Inserted {} languages into session {}", allLanguages.size(), sessionId);

        // Вставляем фреймворки
        for (Framework framework : allFrameworks) {
            FrameworkRecommended result = new FrameworkRecommended(framework, 0.0);
            frameworkResults.add(result);
            kieSession.insert(result);
        }
        log.debug("Inserted {} frameworks into session {}", allFrameworks.size(), sessionId);

        // Вставляем хранилища данных
        for (DataStorage dataStorage : allDataStorages) {
            DataStorageRecommended result = new DataStorageRecommended(dataStorage, 0.0);
            dataStorageResults.add(result);
            kieSession.insert(result);
        }
        log.debug("Inserted {} data storages into session {}", allDataStorages.size(), sessionId);

        log.info("Starting rule execution for session {}", sessionId);
        log.info("Processing {} languages, {} frameworks, {} data storages",
                languageResults.size(), frameworkResults.size(), dataStorageResults.size());

        // Выполняем правила
        int rulesFired = kieSession.fireAllRules();
        log.info("Rules fired in session {}: {}", sessionId, rulesFired);

        // Логируем выполнение правил
        explanationService.logRuleExecution(sessionId, kieSession);

        // Генерируем объяснения
        explanationService.generateExplanations(sessionId, languageResults,
                frameworkResults, dataStorageResults, request);

        kieSession.dispose();
        log.debug("KieSession {} disposed", sessionId);

        // Фильтруем и сортируем результаты
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

        // Формируем ответ
        ProjectRecommendedResponse response = new ProjectRecommendedResponse();
        response.setLanguageRecommendedList(recommendedLanguages);
        response.setFrameworkRecommendedList(recommendedFrameworks);
        response.setDataStorageRecommendedList(recommendedDataStorages);

        // Добавляем matching scores и метаданные
        Map<String, Object> matchingScores = new HashMap<>();
        matchingScores.put("session_id", sessionId.toString());
        matchingScores.put("rules_fired", rulesFired);
        matchingScores.put("total_languages_processed", languageResults.size());
        matchingScores.put("total_frameworks_processed", frameworkResults.size());
        matchingScores.put("total_datastorages_processed", dataStorageResults.size());
        matchingScores.put("recommended_languages_count", recommendedLanguages.size());
        matchingScores.put("recommended_frameworks_count", recommendedFrameworks.size());
        matchingScores.put("recommended_datastorages_count", recommendedDataStorages.size());

        // Добавляем статистику по баллам
        if (!languageResults.isEmpty()) {
            DoubleSummaryStatistics langStats = languageResults.stream()
                    .mapToDouble(LanguageRecommended::getScore)
                    .summaryStatistics();
            matchingScores.put("language_scores_stats", Map.of(
                    "max", langStats.getMax(),
                    "min", langStats.getMin(),
                    "avg", langStats.getAverage()
            ));
        }

        if (!frameworkResults.isEmpty()) {
            DoubleSummaryStatistics frameworkStats = frameworkResults.stream()
                    .mapToDouble(FrameworkRecommended::getScore)
                    .summaryStatistics();
            matchingScores.put("framework_scores_stats", Map.of(
                    "max", frameworkStats.getMax(),
                    "min", frameworkStats.getMin(),
                    "avg", frameworkStats.getAverage()
            ));
        }

        if (!dataStorageResults.isEmpty()) {
            DoubleSummaryStatistics storageStats = dataStorageResults.stream()
                    .mapToDouble(DataStorageRecommended::getScore)
                    .summaryStatistics();
            matchingScores.put("datastorage_scores_stats", Map.of(
                    "max", storageStats.getMax(),
                    "min", storageStats.getMin(),
                    "avg", storageStats.getAverage()
            ));
        }

        response.setMatchingScores(matchingScores);

        // Очищаем данные слушателя для этой сессии
        agendaEventListener.clearSessionData(sessionId);

        log.info("Recommendations generated for session {}. Languages: {}, Frameworks: {}, DataStorages: {}",
                sessionId, recommendedLanguages.size(), recommendedFrameworks.size(),
                recommendedDataStorages.size());

        return response;
    }
}
