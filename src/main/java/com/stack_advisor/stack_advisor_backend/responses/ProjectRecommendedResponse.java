package com.stack_advisor.stack_advisor_backend.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.DataStorage;
import com.stack_advisor.stack_advisor_backend.models.Framework;
import com.stack_advisor.stack_advisor_backend.models.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectRecommendedResponse {
    @JsonProperty("language_recommended_list")
    private List<Language> languageRecommendedList = new ArrayList<>();

    @JsonProperty("framework_recommended_list")
    private List<Framework> frameworkRecommendedList = new ArrayList<>();

    @JsonProperty("data_storage_recommended_list")
    private List<DataStorage> dataStorageRecommendedList = new ArrayList<>();

    @JsonProperty("matching_scores")
    private Map<String, Object> matchingScores = new HashMap<>();

    @JsonProperty("explanations_available")
    private Boolean explanationsAvailable = true;

    @JsonProperty("explanation_endpoint")
    private String explanationEndpoint = "/api/explanations/session/";
}
