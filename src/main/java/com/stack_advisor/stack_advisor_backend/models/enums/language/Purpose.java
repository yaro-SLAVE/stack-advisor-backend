package com.stack_advisor.stack_advisor_backend.models.enums.language;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Purpose {
    @JsonProperty("universal") UNIVERSAL,
    @JsonProperty("specialized") SPECIALIZED
}
