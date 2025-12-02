package com.stack_advisor.stack_advisor_backend.models.enums.project;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AppType {
    @JsonProperty("web") WEB,
    @JsonProperty("mobile") MOBILE,
    @JsonProperty("desktop") DESKTOP
}
