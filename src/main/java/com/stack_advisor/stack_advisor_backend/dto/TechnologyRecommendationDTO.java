package com.stack_advisor.stack_advisor_backend.dto;

import com.stack_advisor.stack_advisor_backend.models.TechnologyRecommendation;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TechnologyRecommendationDTO implements Serializable {
    private Long id;
    private TechnologyDTO technology;
    private Double confidence;
    private String reason;
    private Integer priority;
    private TechnologyRecommendation.RecommendationStatus status;

    public static TechnologyRecommendationDTO fromEntity(TechnologyRecommendation recommendation) {
        if (recommendation == null) {
            return null;
        }

        TechnologyRecommendationDTO dto = new TechnologyRecommendationDTO();
        dto.setId(recommendation.getId());
        dto.setTechnology(TechnologyDTO.fromEntity(recommendation.getTechnology()));
        dto.setConfidence(recommendation.getConfidence());
        dto.setReason(recommendation.getReason());
        dto.setPriority(recommendation.getPriority());
        dto.setStatus(recommendation.getStatus());

        return dto;
    }
}
