package com.stack_advisor.stack_advisor_backend.models.enums.language;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Purpose {
    @JsonProperty("universal") UNIVERSAL,
    @JsonProperty("web_backend") WEB_BACKEND,
    @JsonProperty("web_frontend") WEB_FRONTEND,
    @JsonProperty("mobile") MOBILE,
    @JsonProperty("desktop") DESKTOP,
    @JsonProperty("data_science") DATA_SCIENCE
}
