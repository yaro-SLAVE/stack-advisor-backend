package com.stack_advisor.stack_advisor_backend.drools.services;

import com.stack_advisor.stack_advisor_backend.models.ProjectRequirements;
import com.stack_advisor.stack_advisor_backend.models.RecommendedStack;
import com.stack_advisor.stack_advisor_backend.models.TechnologyStack;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StackAdvisorService {
    @Autowired
    private KieContainer kieContainer;

    public RecommendedStack recommendStack(ProjectRequirements requirements) {
        RecommendedStack recommendation = new RecommendedStack();
        recommendation.setRequirements(requirements);

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("recommendation", recommendation);
        kieSession.insert(requirements);
        kieSession.fireAllRules();
        kieSession.dispose();

        if (!recommendation.getRecommendedStacks().isEmpty()) {
//            TechnologyStack primary = recommendation.getRecommendedStacks().stream()
//                    .max((s1, s2) -> Integer.compare(s1.getConfidenceScore(), s2.getConfidenceScore()))
//                    .orElse(recommendation.getRecommendedStacks().get(0));
//            recommendation.setPrimaryRecommendation(primary);
        }

        return recommendation;
    }
}
