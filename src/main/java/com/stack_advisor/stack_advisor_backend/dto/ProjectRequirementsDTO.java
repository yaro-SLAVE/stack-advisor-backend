package com.stack_advisor.stack_advisor_backend.dto;

import com.stack_advisor.stack_advisor_backend.models.ProjectRequirements;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ProjectRequirementsDTO implements Serializable {
    private Long id;
    private ProjectRequirements.ProjectType projectType;
    private ProjectRequirements.TeamExperience teamExperience;
    private Boolean needHighLoad;
    private Boolean needRealTime;
    private Boolean needHighSecurity;
    private ProjectRequirements.BudgetLevel budget;
    private ProjectRequirements.TimeToMarket timeToMarket;
    private ProjectRequirements.TeamSize teamSize;
    private String explanationChain;
    private LocalDateTime createdAt;

    public static ProjectRequirementsDTO fromEntity(ProjectRequirements requirements) {
        if (requirements == null) {
            return null;
        }

        ProjectRequirementsDTO dto = new ProjectRequirementsDTO();
        dto.setId(requirements.getId());
        dto.setProjectType(requirements.getProjectType());
        dto.setTeamExperience(requirements.getTeamExperience());
        dto.setNeedHighLoad(requirements.getNeedHighLoad());
        dto.setNeedRealTime(requirements.getNeedRealTime());
        dto.setNeedHighSecurity(requirements.getNeedHighSecurity());
        dto.setBudget(requirements.getBudget());
        dto.setTimeToMarket(requirements.getTimeToMarket());
        dto.setTeamSize(requirements.getTeamSize());
        dto.setExplanationChain(requirements.getExplanationChain());
        dto.setCreatedAt(requirements.getCreatedAt());

        return dto;
    }
}
