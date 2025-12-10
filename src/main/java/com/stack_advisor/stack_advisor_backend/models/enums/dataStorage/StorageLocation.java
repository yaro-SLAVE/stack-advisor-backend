package com.stack_advisor.stack_advisor_backend.models.enums.dataStorage;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StorageLocation {
    @JsonProperty("local") LOCAL,
    @JsonProperty("remote") REMOTE
}
