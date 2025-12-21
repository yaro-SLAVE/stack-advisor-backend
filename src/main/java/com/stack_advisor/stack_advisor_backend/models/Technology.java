package com.stack_advisor.stack_advisor_backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "technologies")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TechnologyCategory category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "complexity_score")
    private Double complexity;

    @Column(name = "scalability_score")
    private Double scalability;

    @Column(name = "community_score")
    private Double communitySize;

    @Column(name = "maturity_score")
    private Double maturity;

    @Column(name = "performance_score")
    private Double performance;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LicenseType license;

    @Column(name = "cloud_native")
    private Boolean cloudNative;

    @Column(name = "microservices_ready")
    private Boolean microservicesReady;

    @Column(name = "best_for", columnDefinition = "TEXT")
    private String bestFor;

    public enum TechnologyCategory {
        BACKEND, FRONTEND, DATABASE, DEVOPS, MOBILE, AI_ML, TESTING
    }

    public enum LicenseType {
        OPEN_SOURCE, COMMERCIAL, FREEMIUM, ENTERPRISE
    }

    public Technology() {
        this.complexity = 0.5;
        this.scalability = 0.5;
        this.communitySize = 0.5;
        this.maturity = 0.5;
        this.performance = 0.5;
        this.cloudNative = false;
        this.microservicesReady = false;
    }
}