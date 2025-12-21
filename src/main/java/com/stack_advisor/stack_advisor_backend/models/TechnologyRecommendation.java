package com.stack_advisor.stack_advisor_backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "technology_recommendations")
public class TechnologyRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirements_id", nullable = false)
    private ProjectRequirements requirements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_id", nullable = false)
    private Technology technology;

    @Column(name = "confidence_score")
    private Double confidence;

    @Column(length = 1000)
    private String reason;

    private Integer priority;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RecommendationStatus status;

    public enum RecommendationStatus {
        PRIMARY, ALTERNATIVE, NOT_RECOMMENDED
    }

    public TechnologyRecommendation() {
        this.confidence = 0.5;
        this.priority = 1;
        this.status = RecommendationStatus.ALTERNATIVE;
    }
}
