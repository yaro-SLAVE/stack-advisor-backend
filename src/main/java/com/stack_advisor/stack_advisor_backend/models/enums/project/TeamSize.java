package com.stack_advisor.stack_advisor_backend.models.enums.project;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TeamSize {
    @JsonProperty("micro") MICRO,
    @JsonProperty("small") SMALL,
    @JsonProperty("medium") MEDIUM,
    @JsonProperty("big") BIG,
    @JsonProperty("unknown") UNKNOWN
}
