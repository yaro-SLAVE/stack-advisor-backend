package com.stack_advisor.stack_advisor_backend.models.enums.language;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EntryThreshold {
    @JsonProperty("high") HIGH,
    @JsonProperty("medium") MEDIUM,
    @JsonProperty("low") LOW
}
