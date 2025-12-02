package com.stack_advisor.stack_advisor_backend.models.enums.project;

import com.fasterxml.jackson.annotation.JsonProperty;

//Скорость реализации
public enum TimeToShow {
    //Краткосрочный
    @JsonProperty("fast") FAST,
    //Среднесрочный
    @JsonProperty("medium") MEDIUM,
    //Долгосрочный
    @JsonProperty("slow") SLOW
}
