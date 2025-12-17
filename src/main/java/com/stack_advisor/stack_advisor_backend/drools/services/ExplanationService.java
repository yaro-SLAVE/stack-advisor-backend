package com.stack_advisor.stack_advisor_backend.drools.services;

import com.stack_advisor.stack_advisor_backend.drools.components.CustomAgendaEventListener;
import com.stack_advisor.stack_advisor_backend.drools.data.DataStorageRecommended;
import com.stack_advisor.stack_advisor_backend.drools.data.FrameworkRecommended;
import com.stack_advisor.stack_advisor_backend.drools.data.LanguageRecommended;
import com.stack_advisor.stack_advisor_backend.models.RecommendationExplanation;
import com.stack_advisor.stack_advisor_backend.models.RuleExecutionLog;
import com.stack_advisor.stack_advisor_backend.repositories.RecommendationExplanationRepository;
import com.stack_advisor.stack_advisor_backend.repositories.RuleExecutionLogRepository;
import com.stack_advisor.stack_advisor_backend.models.DataStorage;
import com.stack_advisor.stack_advisor_backend.models.Framework;
import com.stack_advisor.stack_advisor_backend.models.Language;
import com.stack_advisor.stack_advisor_backend.requests.DataStorageRequirementsRequest;
import com.stack_advisor.stack_advisor_backend.requests.FrameworkRequirementsRequest;
import com.stack_advisor.stack_advisor_backend.requests.LanguageRequirementsRequest;
import com.stack_advisor.stack_advisor_backend.requests.ProjectRequirementsRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ExplanationService {

    @Autowired
    private RuleExecutionLogRepository ruleExecutionLogRepository;

    @Autowired
    private RecommendationExplanationRepository recommendationExplanationRepository;

    @Autowired
    private CustomAgendaEventListener agendaEventListener;

    public void logRuleExecution(Long sessionId, KieSession kieSession) {
        try {
            List<CustomAgendaEventListener.RuleExecution> executions =
                    agendaEventListener.getRuleExecutions(sessionId);

            List<CustomAgendaEventListener.RuleActivation> activations =
                    agendaEventListener.getRuleActivations(sessionId);

            log.info("Logging {} rule executions for session {}", executions.size(), sessionId);

            for (CustomAgendaEventListener.RuleExecution execution : executions) {
                String activatedObjects = activations.stream()
                        .filter(ra -> ra.getRuleName().equals(execution.getRuleName()))
                        .findFirst()
                        .map(ra -> String.join("; ", ra.getActivatedObjects()))
                        .orElse("No objects activated");

                RuleExecutionLog logEntry = new RuleExecutionLog(
                        sessionId.toString(),
                        execution.getRuleName(),
                        activatedObjects,
                        String.join("; ", execution.getScoreChanges()),
                        new ObjectMapper().writeValueAsString(execution.getContext())
                );

                ruleExecutionLogRepository.save(logEntry);
                log.debug("Logged execution of rule '{}' for session {}",
                        execution.getRuleName(), sessionId);
            }

        } catch (Exception e) {
            log.error("Error logging rule executions for session {}", sessionId, e);
        }
    }

    public void generateExplanations(Long sessionId,
                                     List<LanguageRecommended> languageResults,
                                     List<FrameworkRecommended> frameworkResults,
                                     List<DataStorageRecommended> dataStorageResults,
                                     ProjectRequirementsRequest request) {

        try {
            List<CustomAgendaEventListener.RuleExecution> ruleExecutions =
                    agendaEventListener.getRuleExecutions(sessionId);

            Map<String, List<CustomAgendaEventListener.RuleExecution>> rulesByItem =
                    new HashMap<>();

            for (CustomAgendaEventListener.RuleExecution execution : ruleExecutions) {
                for (String scoreChange : execution.getScoreChanges()) {
                    // Извлекаем ID элемента из строки изменения балла
                    String itemId = extractItemIdFromScoreChange(scoreChange);
                    if (itemId != null) {
                        rulesByItem.computeIfAbsent(itemId, k -> new ArrayList<>())
                                .add(execution);
                    }
                }
            }

            generateLanguageExplanations(sessionId, languageResults, request, rulesByItem);

            generateFrameworkExplanations(sessionId, frameworkResults, request, rulesByItem);

            generateDataStorageExplanations(sessionId, dataStorageResults, request, rulesByItem);

            log.info("Generated explanations for session {}", sessionId);
        } catch (Exception e) {
            log.error("Error generating explanations for session {}", sessionId, e);
        }
    }

    private void generateLanguageExplanations(Long sessionId,
                                              List<LanguageRecommended> languageResults,
                                              ProjectRequirementsRequest request,
                                              Map<String, List<CustomAgendaEventListener.RuleExecution>> rulesByItem) {
        for (LanguageRecommended result : languageResults) {
            List<String> explanations = new ArrayList<>();
            Language language = result.getLanguage();

            if (request.getLanguageRequirements() != null) {
                LanguageRequirementsRequest req = request.getLanguageRequirements();

                if (req.getEntryThreshold() != null &&
                        language.getEntryThreshold() == req.getEntryThreshold()) {
                    explanations.add(String.format(
                            "Соответствует требуемому порогу вхождения: %s",
                            req.getEntryThreshold()));
                }

                if (req.getExecutionModel() != null &&
                        language.getExecutionModel() == req.getExecutionModel()) {
                    explanations.add(String.format(
                            "✓ Соответствует требуемой модели исполнения: %s",
                            req.getExecutionModel()));
                }

                if (req.getPopularity() != null &&
                        language.getPopularity() == req.getPopularity()) {
                    explanations.add(String.format(
                            "Соответствует требуемой популярности: %s",
                            req.getPopularity()));
                }

                if (req.getPurpose() != null &&
                        language.getPurpose() == req.getPurpose()) {
                    explanations.add(String.format(
                            "Подходит для цели: %s",
                            req.getPurpose()));
                }
            }

            String itemKey = "language_" + language.getId();
            if (rulesByItem.containsKey(itemKey)) {
                rulesByItem.get(itemKey).forEach(rule -> {
                    explanations.add(String.format(
                            "Применено правило '%s'", rule.getRuleName()));
                });
            }

            if (result.getScore() > 0) {
                explanations.add(String.format(
                        "Итоговый балл: %.2f", result.getScore()));
            }

            saveExplanation(sessionId, "LANGUAGE",
                    language.getId(), language.getName(),
                    result.getScore(), explanations);
        }
    }

    private void generateFrameworkExplanations(Long sessionId,
                                               List<FrameworkRecommended> frameworkResults,
                                               ProjectRequirementsRequest request,
                                               Map<String, List<CustomAgendaEventListener.RuleExecution>> rulesByItem) {
        for (FrameworkRecommended result : frameworkResults) {
            List<String> explanations = new ArrayList<>();
            Framework framework = result.getFramework();

            if (request.getFrameworkRequirements() != null) {
                FrameworkRequirementsRequest req = request.getFrameworkRequirements();

                if (req.getIsReactive() != null &&
                        framework.getIsReactive() == req.getIsReactive()) {
                    explanations.add(String.format(
                            "Соответствует требованию реактивности: %s",
                            req.getIsReactive()));
                }

                if (req.getIsActual() != null &&
                        framework.getIsActual() == req.getIsActual()) {
                    explanations.add(String.format(
                            "Соответствует требованию актуальности: %s",
                            req.getIsActual()));
                }

                if (req.getTasksType() != null &&
                        framework.getTasksType() == req.getTasksType()) {
                    explanations.add(String.format(
                            "Подходит для типа задач: %s",
                            req.getTasksType()));
                }
            }

            if (request.getLanguages() != null && !request.getLanguages().isEmpty()) {
                boolean hasMatchingLanguage = framework.getLanguages().stream()
                        .anyMatch(lang -> request.getLanguages().contains(lang.getId()));

                if (hasMatchingLanguage) {
                    explanations.add("Поддерживает выбранные языки программирования");
                }
            }

            String itemKey = "framework_" + framework.getId();
            if (rulesByItem.containsKey(itemKey)) {
                rulesByItem.get(itemKey).forEach(rule -> {
                    explanations.add(String.format(
                            "Применено правило '%s'", rule.getRuleName()));
                });
            }

            if (result.getScore() > 0) {
                explanations.add(String.format(
                        "Итоговый балл: %.2f", result.getScore()));
            }

            saveExplanation(sessionId, "FRAMEWORK",
                    framework.getId(), framework.getName(),
                    result.getScore(), explanations);
        }
    }

    private void generateDataStorageExplanations(Long sessionId,
                                                 List<DataStorageRecommended> dataStorageResults,
                                                 ProjectRequirementsRequest request,
                                                 Map<String, List<CustomAgendaEventListener.RuleExecution>> rulesByItem) {
        for (DataStorageRecommended result : dataStorageResults) {
            List<String> explanations = new ArrayList<>();
            DataStorage dataStorage = result.getDataStorage();

            if (request.getDataStorageRequirements() != null) {
                DataStorageRequirementsRequest req = request.getDataStorageRequirements();

                if (req.getStorageType() != null &&
                        dataStorage.getStorageType() == req.getStorageType()) {
                    explanations.add(String.format(
                            "Соответствует типу хранилища: %s",
                            req.getStorageType()));
                }

                if (req.getStorageLocation() != null &&
                        dataStorage.getStorageLocation() == req.getStorageLocation()) {
                    explanations.add(String.format(
                            "Соответствует местоположению: %s",
                            req.getStorageLocation()));
                }

                if (req.getDataBaseType() != null &&
                        dataStorage.getDataBaseType() == req.getDataBaseType()) {
                    explanations.add(String.format(
                            "Соответствует типу БД: %s",
                            req.getDataBaseType()));
                }
            }

            String itemKey = "datastorage_" + dataStorage.getId();
            if (rulesByItem.containsKey(itemKey)) {
                rulesByItem.get(itemKey).forEach(rule -> {
                    explanations.add(String.format(
                            "Применено правило '%s'", rule.getRuleName()));
                });
            }

            if (result.getScore() > 0) {
                explanations.add(String.format(
                        "Итоговый балл: %.2f", result.getScore()));
            }

            saveExplanation(sessionId, "DATA_STORAGE",
                    dataStorage.getId(), dataStorage.getName(),
                    result.getScore(), explanations);
        }
    }

    private String extractItemIdFromScoreChange(String scoreChange) {
        Pattern pattern = Pattern.compile("\\(ID: (\\d+)\\)");
        Matcher matcher = pattern.matcher(scoreChange);
        if (matcher.find()) {
            String type = scoreChange.toLowerCase().contains("language") ? "language" :
                    scoreChange.toLowerCase().contains("framework") ? "framework" :
                            scoreChange.toLowerCase().contains("datastorage") ? "datastorage" : null;

            if (type != null) {
                return type + "_" + matcher.group(1);
            }
        }
        return null;
    }

    private void saveExplanation(Long sessionId, String type,
                                 Integer itemId, String itemName,
                                 Double finalScore, List<String> explanations) {
        try {
            String explanationsJson = new ObjectMapper()
                    .writeValueAsString(explanations);

            RecommendationExplanation explanation = new RecommendationExplanation(
                    sessionId.toString(),
                    type,
                    itemId,
                    itemName,
                    finalScore,
                    explanationsJson
            );

            recommendationExplanationRepository.save(explanation);
            log.debug("Saved explanation for {} {} in session {}", type, itemId, sessionId);
        } catch (Exception e) {
            log.error("Error saving explanation for item {}:{} in session {}",
                    type, itemId, sessionId, e);
        }
    }

    public Map<String, Object> getSessionSummary(Long sessionId) {
        Map<String, Object> summary = new HashMap<>();

        List<RecommendationExplanation> explanations =
                recommendationExplanationRepository.findBySessionId(sessionId.toString());

        List<RuleExecutionLog> logs =
                ruleExecutionLogRepository.findBySessionId(sessionId.toString());

        summary.put("sessionId", sessionId);
        summary.put("totalExplanations", explanations.size());
        summary.put("totalRulesExecuted", logs.size());
        summary.put("sessionCreated", LocalDateTime.now().toString());

        Map<String, Long> explanationsByType = explanations.stream()
                .collect(Collectors.groupingBy(
                        RecommendationExplanation::getRecommendationType,
                        Collectors.counting()
                ));
        summary.put("explanationsByType", explanationsByType);

        Map<String, Long> rulesExecuted = logs.stream()
                .collect(Collectors.groupingBy(
                        RuleExecutionLog::getRuleName,
                        Collectors.counting()
                ));
        summary.put("rulesExecuted", rulesExecuted);

        Double averageScore = explanations.stream()
                .mapToDouble(RecommendationExplanation::getFinalScore)
                .average()
                .orElse(0.0);
        summary.put("averageRecommendationScore", String.format("%.2f", averageScore));

        List<Map<String, Object>> topRecommendations = explanations.stream()
                .sorted((a, b) -> Double.compare(b.getFinalScore(), a.getFinalScore()))
                .limit(5)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", e.getRecommendationType());
                    map.put("name", e.getItemName());
                    map.put("score", String.format("%.2f", e.getFinalScore()));
                    return map;
                })
                .toList();

        return summary;
    }

    public List<RecommendationExplanation> getExplanations(Long sessionId) {
        return recommendationExplanationRepository.findBySessionId(sessionId.toString());
    }

    public List<RuleExecutionLog> getRuleExecutionLogs(Long sessionId) {
        return ruleExecutionLogRepository.findBySessionId(sessionId.toString());
    }
}