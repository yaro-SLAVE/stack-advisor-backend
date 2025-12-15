package com.stack_advisor.stack_advisor_backend.drools.components;

import com.stack_advisor.stack_advisor_backend.drools.data.DataStorageRecommended;
import com.stack_advisor.stack_advisor_backend.drools.data.FrameworkRecommended;
import com.stack_advisor.stack_advisor_backend.drools.data.LanguageRecommended;
import com.stack_advisor.stack_advisor_backend.requests.ProjectRequirementsRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieRuntime;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CustomAgendaEventListener implements AgendaEventListener {

    private final Map<Long, List<RuleActivation>> ruleActivations = new ConcurrentHashMap<>();
    private final Map<Long, List<RuleExecution>> ruleExecutions = new ConcurrentHashMap<>();

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        String ruleName = event.getMatch().getRule().getName();
        Long sessionId = getSessionId(event.getKieRuntime());

        if (sessionId == null) {
            log.warn("Cannot get session ID for rule activation: {}", ruleName);
            return;
        }

        List<String> activatedObjects = new ArrayList<>();

        event.getMatch().getObjects().forEach(obj -> {
            if (obj instanceof LanguageRecommended) {
                LanguageRecommended lr = (LanguageRecommended) obj;
                activatedObjects.add("Language: " + lr.getLanguage().getName() + " (ID: " + lr.getLanguage().getId() + ")");
            } else if (obj instanceof FrameworkRecommended) {
                FrameworkRecommended fr = (FrameworkRecommended) obj;
                activatedObjects.add("Framework: " + fr.getFramework().getName() + " (ID: " + fr.getFramework().getId() + ")");
            } else if (obj instanceof DataStorageRecommended) {
                DataStorageRecommended dsr = (DataStorageRecommended) obj;
                activatedObjects.add("DataStorage: " + dsr.getDataStorage().getName() + " (ID: " + dsr.getDataStorage().getId() + ")");
            } else if (obj instanceof ProjectRequirementsRequest) {
                activatedObjects.add("Project Requirements Request");
            }
        });

        RuleActivation activation = new RuleActivation(
                ruleName,
                activatedObjects,
                LocalDateTime.now()
        );

        ruleActivations.computeIfAbsent(sessionId, k -> new ArrayList<>()).add(activation);

        log.debug("Rule '{}' activated in session {} with objects: {}",
                ruleName, sessionId, activatedObjects);
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        String ruleName = event.getMatch().getRule().getName();
        Long sessionId = getSessionId(event.getKieRuntime());
        log.debug("Rule '{}' cancelled in session {}", ruleName, sessionId);
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        String ruleName = event.getMatch().getRule().getName();
        Long sessionId = getSessionId(event.getKieRuntime());
        log.debug("Before firing rule '{}' in session {}", ruleName, sessionId);
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        String ruleName = event.getMatch().getRule().getName();
        Long sessionId = getSessionId(event.getKieRuntime());

        if (sessionId == null) {
            log.warn("Cannot get session ID for rule execution: {}", ruleName);
            return;
        }

        List<String> scoreChanges = new ArrayList<>();
        Map<String, Object> context = new HashMap<>();

        // Сохраняем контекст правила
        context.put("rule", ruleName);
        context.put("timestamp", LocalDateTime.now().toString());
        context.put("sessionId", sessionId);

        event.getMatch().getObjects().forEach(obj -> {
            if (obj instanceof LanguageRecommended) {
                LanguageRecommended lr = (LanguageRecommended) obj;
                scoreChanges.add(String.format("Language '%s' (ID: %d) score: %.2f",
                        lr.getLanguage().getName(), lr.getLanguage().getId(), lr.getScore()));
                context.put("language_" + lr.getLanguage().getId(), lr.getScore());
            } else if (obj instanceof FrameworkRecommended) {
                FrameworkRecommended fr = (FrameworkRecommended) obj;
                scoreChanges.add(String.format("Framework '%s' (ID: %d) score: %.2f",
                        fr.getFramework().getName(), fr.getFramework().getId(), fr.getScore()));
                context.put("framework_" + fr.getFramework().getId(), fr.getScore());
            } else if (obj instanceof DataStorageRecommended) {
                DataStorageRecommended dsr = (DataStorageRecommended) obj;
                scoreChanges.add(String.format("DataStorage '%s' (ID: %d) score: %.2f",
                        dsr.getDataStorage().getName(), dsr.getDataStorage().getId(), dsr.getScore()));
                context.put("datastorage_" + dsr.getDataStorage().getId(), dsr.getScore());
            }
        });

        RuleExecution execution = new RuleExecution(
                ruleName,
                scoreChanges,
                context,
                LocalDateTime.now()
        );

        ruleExecutions.computeIfAbsent(sessionId, k -> new ArrayList<>()).add(execution);
        log.info("Rule '{}' fired in session {}. Score changes: {}",
                ruleName, sessionId, scoreChanges);
    }

    // Остальные методы интерфейса (не требуются для нашей логики)
    @Override public void agendaGroupPopped(AgendaGroupPoppedEvent event) {}
    @Override public void agendaGroupPushed(AgendaGroupPushedEvent event) {}
    @Override public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {}
    @Override public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {}
    @Override public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {}
    @Override public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {}

    public List<RuleActivation> getRuleActivations(Long sessionId) {
        return ruleActivations.getOrDefault(sessionId, new ArrayList<>());
    }

    public List<RuleExecution> getRuleExecutions(Long sessionId) {
        return ruleExecutions.getOrDefault(sessionId, new ArrayList<>());
    }

    public void clearSessionData(Long sessionId) {
        ruleActivations.remove(sessionId);
        ruleExecutions.remove(sessionId);
        log.debug("Cleared data for session {}", sessionId);
    }

    private Long getSessionId(KieRuntime kieRuntime) {
        if (kieRuntime instanceof KieSession) {
            return ((KieSession) kieRuntime).getIdentifier();
        }

        return (long) System.identityHashCode(kieRuntime);
    }

    @Getter
    @AllArgsConstructor
    public static class RuleActivation {
        private String ruleName;
        private List<String> activatedObjects;
        private LocalDateTime timestamp;
    }

    @Getter
    @AllArgsConstructor
    public static class RuleExecution {
        private String ruleName;
        private List<String> scoreChanges;
        private Map<String, Object> context;
        private LocalDateTime timestamp;
    }
}