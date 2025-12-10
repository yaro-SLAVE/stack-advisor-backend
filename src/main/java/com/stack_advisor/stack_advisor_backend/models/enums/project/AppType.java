package com.stack_advisor.stack_advisor_backend.models.enums.project;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AppType {
    @JsonProperty("web") WEB,
    @JsonProperty("android") ANDROID,
    @JsonProperty("ios") IOS,
    @JsonProperty("desktop") DESKTOP
}
