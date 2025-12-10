package com.stack_advisor.stack_advisor_backend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.enums.framework.TasksType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FrameworkRequirementsRequest {
    @JsonProperty("is_reactive")
    private Boolean isReactive;
    @JsonProperty("is_actual")
    private Boolean isActual;
    @JsonProperty("tasks_type")
    private TasksType tasksType;
}
