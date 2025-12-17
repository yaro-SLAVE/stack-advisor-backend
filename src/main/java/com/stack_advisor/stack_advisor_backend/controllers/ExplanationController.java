package com.stack_advisor.stack_advisor_backend.controllers;

import com.stack_advisor.stack_advisor_backend.drools.services.ExplanationService;
import com.stack_advisor.stack_advisor_backend.models.RecommendationExplanation;
import com.stack_advisor.stack_advisor_backend.models.RuleExecutionLog;
import com.stack_advisor.stack_advisor_backend.repositories.RuleExecutionLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/explanations")
@CrossOrigin(origins = "*")
@Slf4j
public class ExplanationController {

    @Autowired
    private ExplanationService explanationService;
    @Autowired
    private RuleExecutionLogRepository ruleExecutionLogRepository;

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<?> getSessionExplanations(@PathVariable String sessionId) {
        try {
            Long id = Long.parseLong(sessionId);

            List<RecommendationExplanation> explanations =
                    explanationService.getExplanations(id);

            List<RuleExecutionLog> logs =
                    explanationService.getRuleExecutionLogs(id);

            Map<String, Object> sessionSummary =
                    explanationService.getSessionSummary(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("sessionId", id);
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("explanations", explanations);
            response.put("ruleExecutionLogs", logs);
            response.put("summary", sessionSummary);
            response.put("totalItems", explanations.size() + logs.size());

            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "error", "Invalid session ID format. Must be a number."
                    ));
        } catch (Exception e) {
            log.error("Error getting explanations for session {}", sessionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "error", "Failed to get explanations",
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/session/{sessionId}/summary")
    public ResponseEntity<?> getSessionSummary(@PathVariable String sessionId) {
        try {
            Long id = Long.parseLong(sessionId);
            Map<String, Object> summary = explanationService.getSessionSummary(id);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "sessionId", id,
                    "summary", summary
            ));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "error", "Invalid session ID format. Must be a number."
                    ));
        } catch (Exception e) {
            log.error("Error getting summary for session {}", sessionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "error", "Failed to get session summary"
                    ));
        }
    }

    @GetMapping("/recent-sessions")
    public ResponseEntity<?> getRecentSessions(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        try {
            List<RuleExecutionLog> response = ruleExecutionLogRepository.findAll();

            response.sort(Comparator.comparing(RuleExecutionLog::getSessionId));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting recent sessions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "error", "Failed to get recent sessions"
                    ));
        }
    }
}
