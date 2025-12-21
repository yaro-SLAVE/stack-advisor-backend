package com.stack_advisor.stack_advisor_backend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExpertSystemResponseDTO implements Serializable {
    private String sessionId;
    private ProjectRequirementsDTO requirements;
    private List<TechnologyRecommendationDTO> recommendations;
    private List<String> explanationChain;
    private List<String> auditLog;
    private Integer rulesFired;
}