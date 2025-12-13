package com.stack_advisor.stack_advisor_backend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.enums.framework.TasksType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class FrameworksListResponse {
    private Integer id;
    private String name;
    private List<LanguagesListResponse> languages;
    @JsonProperty("is_reactive")
    private Boolean isReactive;
    @JsonProperty("last_updated_at")
    private LocalDate lastUpdatedAt;
    @JsonProperty("is_actual")
    private Boolean isActual;
    @JsonProperty("tasks_type")
    private TasksType tasksType;
}
