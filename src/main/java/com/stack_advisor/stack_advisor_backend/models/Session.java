package com.stack_advisor.stack_advisor_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", unique = true, nullable = false, length = 36)
    private String sessionId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requirements_id", referencedColumnName = "id")
    private ProjectRequirements requirements;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RuleExecutionLog> executionLogs = new ArrayList<>();

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "total_rules_fired")
    private Integer totalRulesFired;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public ProjectRequirements getRequirements() { return requirements; }
    public void setRequirements(ProjectRequirements requirements) { this.requirements = requirements; }

    public List<RuleExecutionLog> getExecutionLogs() { return executionLogs; }
    public void setExecutionLogs(List<RuleExecutionLog> executionLogs) { this.executionLogs = executionLogs; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getEndedAt() { return endedAt; }
    public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; }

    public Integer getTotalRulesFired() { return totalRulesFired; }
    public void setTotalRulesFired(Integer totalRulesFired) { this.totalRulesFired = totalRulesFired; }

    // Переопределяем toString БЕЗ циклических ссылок
    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                ", totalRulesFired=" + totalRulesFired +
                ", executionLogsCount=" + (executionLogs != null ? executionLogs.size() : 0) +
                ", requirementsId=" + (requirements != null ? requirements.getId() : null) +
                '}';
    }

    // equals и hashCode только по id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    protected void onCreate() {
        startedAt = LocalDateTime.now();
        sessionId = java.util.UUID.randomUUID().toString();
        if (totalRulesFired == null) {
            totalRulesFired = 0;
        }
    }
}
