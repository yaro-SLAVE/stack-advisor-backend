package com.stack_advisor.stack_advisor_backend.models.enums.project;

import com.fasterxml.jackson.annotation.JsonProperty;

//Скорость реализации
public enum TimeToShow {
    @JsonProperty("fast") FAST,
    @JsonProperty("medium") MEDIUM,
    @JsonProperty("slow") SLOW
}
