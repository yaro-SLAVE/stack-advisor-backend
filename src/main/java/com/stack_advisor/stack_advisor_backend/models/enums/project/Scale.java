package com.stack_advisor.stack_advisor_backend.models.enums.project;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Scale {
    @JsonProperty("complex") COMPLEX,
    @JsonProperty("easy") EASY,
    @JsonProperty("hard") HARD,
    @JsonProperty("unknown") UNKNOWN
}
