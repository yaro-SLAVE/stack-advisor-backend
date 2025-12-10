package com.stack_advisor.stack_advisor_backend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.enums.project.*;
import lombok.Getter;

import java.util.List;

@Getter
public class ProjectRequirementsRequest {
    @JsonProperty("app_type")
    private AppType appType;
    @JsonProperty("team_size")
    private TeamSize teamSize;
    @JsonProperty("project_type")
    private ProjectType projectType;
    private Scale scale;
    @JsonProperty("time_to_show")
    private TimeToShow timeToShow;
    private List<String> languages;
    private List<String> frameworks;
    @JsonProperty("data_storages")
    private List<String> dataStorages;
    @JsonProperty("language_requirements")
    private LanguageRequirementsRequest languageRequirements;
    @JsonProperty("framework_requirements")
    private FrameworkRequirementsRequest frameworkRequirements;
    @JsonProperty("data_storage_requirements")
    private DataStorageRequirementsRequest dataStorageRequirements;
}
