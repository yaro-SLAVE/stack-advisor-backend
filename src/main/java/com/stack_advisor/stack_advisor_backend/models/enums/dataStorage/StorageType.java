package com.stack_advisor.stack_advisor_backend.models.enums.dataStorage;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StorageType {
    @JsonProperty("relation") RELATIONAL,
    @JsonProperty("document") DOCUMENT,
    @JsonProperty("key_value") KEY_VALUE
}
