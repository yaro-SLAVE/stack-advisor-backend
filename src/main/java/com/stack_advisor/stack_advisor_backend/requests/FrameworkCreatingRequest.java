package com.stack_advisor.stack_advisor_backend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.enums.framework.TasksType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FrameworkCreatingRequest {
    private String name;
    private List<Integer> languages;
    @JsonProperty("is_reactive")
    private Boolean isReactive;
    @JsonProperty("last_updated_at")
    private LocalDate lastUpdatedAt;
    @JsonProperty("is_actual")
    private Boolean isActual;
    @JsonProperty("tasks_type")
    private TasksType tasksType;
}
