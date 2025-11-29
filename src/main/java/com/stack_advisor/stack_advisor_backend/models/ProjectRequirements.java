package com.stack_advisor.stack_advisor_backend.models;

import com.stack_advisor.stack_advisor_backend.models.enums.ProjectType;
import com.stack_advisor.stack_advisor_backend.models.enums.TimeToShow;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ProjectRequirements {
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;
    private Integer teamSize;
    @Enumerated(EnumType.STRING)
    private TimeToShow timeToShow;
    private String scalability;
    private String budget;
    private String teamExpertise;
    private List<String> preferredLanguages;
    private boolean microservices;
    private boolean cloudNative;
    private boolean realTime;
    private String securityLevel;
    private String databaseRequirements;
}
