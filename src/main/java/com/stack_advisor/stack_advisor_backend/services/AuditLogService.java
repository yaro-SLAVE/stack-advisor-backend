package com.stack_advisor.stack_advisor_backend.services;

import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditLogService {

    private final List<String> auditLog = new ArrayList<>();
    private StringWriter auditLogWriter;

    public void startAuditLog(String sessionId) {
        auditLog.clear();
        auditLog.add("=== Начало сессии экспертной системы: " + sessionId + " ===");
        auditLog.add("Время: " + java.time.LocalDateTime.now());
    }

    public void logRuleActivation(String ruleName, String facts) {
        String entry = String.format("[RULE] Активировано правило: %s\nФакты: %s",
                ruleName, facts);
        auditLog.add(entry);
    }

    public void logConclusion(String conclusion) {
        auditLog.add("[CONCLUSION] " + conclusion);
    }

    public void logInfo(String message) {
        auditLog.add("[INFO] " + message);
    }

    public void endAuditLog() {
        auditLog.add("=== Конец сессии ===");
        auditLog.add("Всего выполнено правил: " + (auditLog.size() - 3));
    }

    public List<String> getAuditLog() {
        return new ArrayList<>(auditLog);
    }

    public String getAuditLogAsString() {
        return String.join("\n", auditLog);
    }
}
