package com.stack_advisor.stack_advisor_backend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.enums.language.EntryThreshold;
import com.stack_advisor.stack_advisor_backend.models.enums.language.ExecutionModel;
import com.stack_advisor.stack_advisor_backend.models.enums.language.Popularity;
import com.stack_advisor.stack_advisor_backend.models.enums.language.Purpose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageCreatingRequest {
    private String name;
    @JsonProperty("entry_threshold")
    private EntryThreshold entryThreshold;
    @JsonProperty("execution_model")
    private ExecutionModel executionModel;
    private Popularity popularity;
    private Purpose purpose;
}
