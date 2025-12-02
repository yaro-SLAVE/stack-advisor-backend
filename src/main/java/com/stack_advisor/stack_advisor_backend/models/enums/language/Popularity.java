package com.stack_advisor.stack_advisor_backend.models.enums.language;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Popularity {
    @JsonProperty("popular") POPULAR,
    @JsonProperty("actual") ACTUAL,
    @JsonProperty("out_of_general_use") OUT_OF_GENERAL_USE
}
