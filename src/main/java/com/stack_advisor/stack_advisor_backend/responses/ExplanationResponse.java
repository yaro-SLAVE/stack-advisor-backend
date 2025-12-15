package com.stack_advisor.stack_advisor_backend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExplanationResponse {
    private boolean success;
    private String sessionId;
    private LocalDateTime timestamp;
    private Object data;
    private String message;
}
