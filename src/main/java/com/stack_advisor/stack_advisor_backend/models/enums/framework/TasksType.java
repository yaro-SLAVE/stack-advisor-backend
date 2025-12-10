package com.stack_advisor.stack_advisor_backend.models.enums.framework;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TasksType {
    @JsonProperty("backend") BACKEND,
    @JsonProperty("frontend") FRONTEND,
    @JsonProperty("mobile") MOBILE
}
