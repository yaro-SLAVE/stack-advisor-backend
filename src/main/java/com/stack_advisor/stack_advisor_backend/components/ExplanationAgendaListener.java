package com.stack_advisor.stack_advisor_backend.components;

import org.kie.api.event.rule.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExplanationAgendaListener implements AgendaEventListener {

    private final List<String> firedRules = new ArrayList<>();
    private final List<RuleMatch> ruleMatches = new ArrayList<>();

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        // Rule matched but not yet fired
    }

    @Override
    public void matchCancelled(MatchCancelledEvent matchCancelledEvent) {

    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent beforeMatchFiredEvent) {

    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        String ruleName = event.getMatch().getRule().getName();
        String rulePackage = event.getMatch().getRule().getPackageName();
        Object[] objects = event.getMatch().getObjects().toArray();

        StringBuilder explanation = new StringBuilder();
        explanation.append("Правило '").append(ruleName).append("' выполнено.\n");
        explanation.append("Соответствующие факты: ");

        for (Object obj : objects) {
            explanation.append(obj.getClass().getSimpleName()).append(", ");
        }

        firedRules.add(explanation.toString());

        RuleMatch match = new RuleMatch();
        match.setRuleName(ruleName);
        match.setRulePackage(rulePackage);
        match.setFacts(objects);
        match.setTimestamp(System.currentTimeMillis());
        ruleMatches.add(match);
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
        // Agenda group popped
    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {
        // Agenda group pushed
    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        // Before rule flow group activated
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        // After rule flow group activated
    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
        // Before rule flow group deactivated
    }

    public List<String> getFiredRules() {
        return new ArrayList<>(firedRules);
    }

    public List<RuleMatch> getRuleMatches() {
        return new ArrayList<>(ruleMatches);
    }

    public void clear() {
        firedRules.clear();
        ruleMatches.clear();
    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
        // Legacy method for compatibility
    }

    public static class RuleMatch {
        private String ruleName;
        private String rulePackage;
        private Object[] facts;
        private long timestamp;

        // Getters and setters
        public String getRuleName() { return ruleName; }
        public void setRuleName(String ruleName) { this.ruleName = ruleName; }
        public String getRulePackage() { return rulePackage; }
        public void setRulePackage(String rulePackage) { this.rulePackage = rulePackage; }
        public Object[] getFacts() { return facts; }
        public void setFacts(Object[] facts) { this.facts = facts; }
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
}
