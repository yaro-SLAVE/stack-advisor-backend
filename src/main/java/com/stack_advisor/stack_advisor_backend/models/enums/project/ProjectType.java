package com.stack_advisor.stack_advisor_backend.models.enums.project;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProjectType {
    @JsonProperty("pet") PET,
    @JsonProperty("research") RESEARCH,
    @JsonProperty("commercial") COMMERCIAL
}
