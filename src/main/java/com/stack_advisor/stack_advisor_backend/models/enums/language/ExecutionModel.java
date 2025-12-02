package com.stack_advisor.stack_advisor_backend.models.enums.language;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ExecutionModel {
    @JsonProperty("interpretable") INTERPRETABLE,
    @JsonProperty("compiled") COMPILED
}
