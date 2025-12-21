package com.stack_advisor.stack_advisor_backend.services;

import com.stack_advisor.stack_advisor_backend.components.ExplanationAgendaListener;
import com.stack_advisor.stack_advisor_backend.models.*;
import com.stack_advisor.stack_advisor_backend.repositories.*;
import jakarta.transaction.Transactional;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StackAdvisorService{
    @Autowired
    private KieSession kieSession;

    @Autowired
    private ExplanationAgendaListener agendaListener;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private ProjectRequirementsRepository requirementsRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private TechnologyRecommendationRepository recommendationRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RuleExecutionLogRepository logRepository;

    @Transactional
    public Map<String, Object> analyzeProject(ProjectRequirements requirements) {
        Session session = new Session();
        session.setRequirements(requirements);
        requirements.setSession(session);

        ProjectRequirements savedRequirements = requirementsRepository.save(requirements);
        sessionRepository.save(session);

        auditLogService.startAuditLog(session.getSessionId());

        agendaListener.clear();

        kieSession.addEventListener(agendaListener);

        try {
            FactHandle requirementsHandle = kieSession.insert(savedRequirements);
            List<FactHandle> technologyHandles = new ArrayList<>();

            List<Technology> allTechnologies = technologyRepository.findAll();
            for (Technology tech : allTechnologies) {
                technologyHandles.add(kieSession.insert(tech));
            }

            List<TechnologyRecommendation> recommendations = new ArrayList<>();
            kieSession.setGlobal("recommendations", recommendations);
            kieSession.setGlobal("auditLogService", auditLogService);

            int rulesFired = kieSession.fireAllRules();

            auditLogService.endAuditLog();

            savedRequirements.setExplanationChain(auditLogService.getAuditLogAsString());
            ProjectRequirements updatedRequirements = requirementsRepository.save(savedRequirements);

            for (TechnologyRecommendation rec : recommendations) {
                rec.setRequirements(updatedRequirements);
                recommendationRepository.save(rec);
            }

            saveExecutionLogs(session, agendaListener.getRuleMatches());

            kieSession.delete(requirementsHandle);
            for (FactHandle handle : technologyHandles) {
                kieSession.delete(handle);
            }

            session.setTotalRulesFired(rulesFired);
            session.setEndedAt(java.time.LocalDateTime.now());
            sessionRepository.save(session);

            Map<String, Object> result = new HashMap<>();
            result.put("sessionId", session.getSessionId());
            result.put("requirements", updatedRequirements);
            result.put("recommendations", recommendations); // Это будут новые рекомендации без ID
            result.put("explanationChain", agendaListener.getFiredRules());
            result.put("auditLog", auditLogService.getAuditLog());
            result.put("rulesFired", rulesFired);

            return result;

        } catch (Exception e) {
            session.setEndedAt(java.time.LocalDateTime.now());
            sessionRepository.save(session);
            throw e;
        }
    }

    private void saveExecutionLogs(Session session, List<ExplanationAgendaListener.RuleMatch> matches) {
        for (ExplanationAgendaListener.RuleMatch match : matches) {
            RuleExecutionLog log = new RuleExecutionLog();
            log.setSession(session);
            log.setRuleName(match.getRuleName());
            log.setFiredRule(match.getRuleName() + " from " + match.getRulePackage());

            if (match.getFacts() != null && match.getFacts().length > 0) {
                StringBuilder factsBuilder = new StringBuilder();
                for (Object fact : match.getFacts()) {
                    if (fact instanceof ProjectRequirements) {
                        factsBuilder.append("ProjectRequirements");
                    } else if (fact instanceof Technology) {
                        factsBuilder.append("Technology");
                    } else {
                        factsBuilder.append(fact.getClass().getSimpleName());
                    }
                    factsBuilder.append(", ");
                }
                log.setMatchedFacts(factsBuilder.toString());
            } else {
                log.setMatchedFacts("No matched facts");
            }

            log.setResult("Rule executed successfully");
            log.setFullExplanation("Правило сработало на основе предоставленных фактов");
            logRepository.save(log);
        }
    }

    public List<TechnologyRecommendation> getRecommendationsBySession(String sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionId);
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            if (session.getRequirements() != null) {
                return recommendationRepository.findByRequirements(session.getRequirements());
            }
        }
        return Collections.emptyList();
    }

    public List<RuleExecutionLog> getExplanationBySession(String sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionId);
        if (sessionOpt.isPresent()) {
            return logRepository.findBySession(sessionOpt.get());
        }
        return Collections.emptyList();
    }

    public List<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }
}
