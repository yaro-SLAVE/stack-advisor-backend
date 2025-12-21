package com.stack_advisor.stack_advisor_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rule_execution_logs")
public class RuleExecutionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Column(name = "rule_name", length = 200)
    private String ruleName;

    @Column(name = "fired_rule", length = 1000)
    private String firedRule;

    @Column(name = "matched_facts", columnDefinition = "TEXT")
    private String matchedFacts;

    @Column(length = 1000)
    private String result;

    @Column(name = "full_explanation", columnDefinition = "TEXT")
    private String fullExplanation;

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}
