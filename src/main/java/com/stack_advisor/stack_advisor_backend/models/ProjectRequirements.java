package com.stack_advisor.stack_advisor_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "project_requirements")
public class ProjectRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", length = 20)
    private ProjectType projectType;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_experience", length = 20)
    private TeamExperience teamExperience;

    @Column(name = "need_high_load")
    private Boolean needHighLoad;

    @Column(name = "need_real_time")
    private Boolean needRealTime;

    @Column(name = "need_high_security")
    private Boolean needHighSecurity;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private BudgetLevel budget;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_to_market", length = 20)
    private TimeToMarket timeToMarket;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_size", length = 20)
    private TeamSize teamSize;

    @Column(name = "team_members")
    private Integer teamMembers;

    @OneToOne(mappedBy = "requirements", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Session session;

    @OneToMany(mappedBy = "requirements", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TechnologyRecommendation> recommendations = new ArrayList<>();

    @Column(name = "explanation_chain", columnDefinition = "TEXT")
    private String explanationChain;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProjectType getProjectType() { return projectType; }
    public void setProjectType(ProjectType projectType) { this.projectType = projectType; }

    public TeamExperience getTeamExperience() { return teamExperience; }
    public void setTeamExperience(TeamExperience teamExperience) { this.teamExperience = teamExperience; }

    public Boolean getNeedHighLoad() { return needHighLoad; }
    public void setNeedHighLoad(Boolean needHighLoad) { this.needHighLoad = needHighLoad; }

    public Boolean getNeedRealTime() { return needRealTime; }
    public void setNeedRealTime(Boolean needRealTime) { this.needRealTime = needRealTime; }

    public Boolean getNeedHighSecurity() { return needHighSecurity; }
    public void setNeedHighSecurity(Boolean needHighSecurity) { this.needHighSecurity = needHighSecurity; }

    public BudgetLevel getBudget() { return budget; }
    public void setBudget(BudgetLevel budget) { this.budget = budget; }

    public TimeToMarket getTimeToMarket() { return timeToMarket; }
    public void setTimeToMarket(TimeToMarket timeToMarket) { this.timeToMarket = timeToMarket; }

    public TeamSize getTeamSize() { return teamSize; }
    public void setTeamSize(TeamSize teamSize) { this.teamSize = teamSize; }

    public Integer getTeamMembers() { return teamMembers; }
    public void setTeamMembers(Integer teamMembers) { this.teamMembers = teamMembers; }

    public Session getSession() { return session; }
    public void setSession(Session session) { this.session = session; }

    public List<TechnologyRecommendation> getRecommendations() { return recommendations; }
    public void setRecommendations(List<TechnologyRecommendation> recommendations) { this.recommendations = recommendations; }

    public String getExplanationChain() { return explanationChain; }
    public void setExplanationChain(String explanationChain) { this.explanationChain = explanationChain; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Переопределяем toString БЕЗ циклических ссылок
    @Override
    public String toString() {
        return "ProjectRequirements{" +
                "id=" + id +
                ", projectType=" + projectType +
                ", teamExperience=" + teamExperience +
                ", needHighLoad=" + needHighLoad +
                ", needRealTime=" + needRealTime +
                ", needHighSecurity=" + needHighSecurity +
                ", budget=" + budget +
                ", timeToMarket=" + timeToMarket +
                ", teamSize=" + teamSize +
                ", teamMembers=" + teamMembers +
                ", explanationChain='" + (explanationChain != null ? explanationChain.substring(0, Math.min(50, explanationChain.length())) + "..." : "null") + '\'' +
                ", createdAt=" + createdAt +
                ", recommendationsCount=" + (recommendations != null ? recommendations.size() : 0) +
                '}';
    }

    // equals и hashCode только по id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectRequirements that = (ProjectRequirements) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (teamMembers == null) {
            teamMembers = 5;
        }
        if (needHighLoad == null) {
            needHighLoad = false;
        }
        if (needRealTime == null) {
            needRealTime = false;
        }
        if (needHighSecurity == null) {
            needHighSecurity = false;
        }
    }

    public enum ProjectType {
        WEB, MOBILE, DESKTOP, AI_ML, DEVOPS, GAME
    }

    public enum TeamExperience {
        JAVA, JAVASCRIPT, PYTHON, DOTNET, PHP, GO, MIXED, NONE
    }

    public enum BudgetLevel {
        LOW, MEDIUM, HIGH, ENTERPRISE
    }

    public enum TimeToMarket {
        FAST, NORMAL, SLOW
    }

    public enum TeamSize {
        SMALL(1, 5), MEDIUM(5, 15), LARGE(15, 50);

        private final int min;
        private final int max;

        TeamSize(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public boolean isInRange(int size) {
            return size >= min && size <= max;
        }
    }
}