package com.stack_advisor.stack_advisor_backend.models.enums.dataStorage;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DataBaseType {
    @JsonProperty("sql") SQL,
    @JsonProperty("no_sql") NO_SQL
}
