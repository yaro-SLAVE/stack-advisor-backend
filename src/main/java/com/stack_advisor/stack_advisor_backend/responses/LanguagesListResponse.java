package com.stack_advisor.stack_advisor_backend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.enums.language.EntryThreshold;
import com.stack_advisor.stack_advisor_backend.models.enums.language.ExecutionModel;
import com.stack_advisor.stack_advisor_backend.models.enums.language.Popularity;
import com.stack_advisor.stack_advisor_backend.models.enums.language.Purpose;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LanguagesListResponse {
    private Integer id;
    private String name;
    @JsonProperty("entry_treshold")
    private EntryThreshold entryThreshold;
    @JsonProperty("execution_model")
    private ExecutionModel executionModel;
    private Popularity popularity;
    private Purpose purpose;
}
