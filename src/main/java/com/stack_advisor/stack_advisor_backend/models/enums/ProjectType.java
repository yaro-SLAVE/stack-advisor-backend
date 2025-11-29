package com.stack_advisor.stack_advisor_backend.models.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProjectType {
    @JsonProperty("web") WEB,
    @JsonProperty("mobile") MOBILE,
    @JsonProperty("desktop") DESKTOP,
    @JsonProperty("unknown") UNKNOWN
}
