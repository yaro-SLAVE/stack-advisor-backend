package com.stack_advisor.stack_advisor_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_explanation")
@NoArgsConstructor
@Getter
@Setter
public class RecommendationExplanation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "recommendation_type")
    private String recommendationType; // "LANGUAGE", "FRAMEWORK", "DATA_STORAGE"

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "final_score")
    private Double finalScore;

    @Column(name = "explanations", length = 4000)
    private String explanations; // JSON array of explanations

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public RecommendationExplanation(String sessionId, String recommendationType,
                                     Integer itemId, String itemName,
                                     Double finalScore, String explanations) {
        this.sessionId = sessionId;
        this.recommendationType = recommendationType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.finalScore = finalScore;
        this.explanations = explanations;
        this.createdAt = LocalDateTime.now();
    }
}
