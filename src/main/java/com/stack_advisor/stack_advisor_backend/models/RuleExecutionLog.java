package com.stack_advisor.stack_advisor_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rule_execution_log")
@NoArgsConstructor
@Getter
@Setter
public class RuleExecutionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "objects_activated", length = 2000)
    private String objectsActivated;

    @Column(name = "score_changes", length = 2000)
    private String scoreChanges;

    @Column(name = "execution_context", length = 4000)
    private String executionContext;

    public RuleExecutionLog(String sessionId, String ruleName,
                            String objectsActivated, String scoreChanges,
                            String executionContext) {
        this.sessionId = sessionId;
        this.ruleName = ruleName;
        this.timestamp = LocalDateTime.now();
        this.objectsActivated = objectsActivated;
        this.scoreChanges = scoreChanges;
        this.executionContext = executionContext;
    }
}
