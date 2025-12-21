package com.stack_advisor.stack_advisor_backend.dto;

import com.stack_advisor.stack_advisor_backend.models.Technology;
import lombok.Data;
import java.io.Serializable;

@Data
public class TechnologyDTO implements Serializable {
    private Long id;
    private String name;
    private Technology.TechnologyCategory category;
    private String description;
    private Double complexity;
    private Double scalability;
    private Double communitySize;
    private Double maturity;
    private Double performance;
    private Technology.LicenseType license;
    private Boolean cloudNative;
    private Boolean microservicesReady;
    private String bestFor;

    public static TechnologyDTO fromEntity(Technology technology) {
        if (technology == null) {
            return null;
        }

        TechnologyDTO dto = new TechnologyDTO();
        dto.setId(technology.getId());
        dto.setName(technology.getName());
        dto.setCategory(technology.getCategory());
        dto.setDescription(technology.getDescription());
        dto.setComplexity(technology.getComplexity());
        dto.setScalability(technology.getScalability());
        dto.setCommunitySize(technology.getCommunitySize());
        dto.setMaturity(technology.getMaturity());
        dto.setPerformance(technology.getPerformance());
        dto.setLicense(technology.getLicense());
        dto.setCloudNative(technology.getCloudNative());
        dto.setMicroservicesReady(technology.getMicroservicesReady());
        dto.setBestFor(technology.getBestFor());

        return dto;
    }
}