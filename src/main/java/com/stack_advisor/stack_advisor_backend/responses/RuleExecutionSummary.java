package com.stack_advisor.stack_advisor_backend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleExecutionSummary {
    private String ruleName;
    private int executionCount;
    private LocalDateTime firstExecution;
    private LocalDateTime lastExecution;
    private List<String> affectedItems;
}
