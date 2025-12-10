package com.stack_advisor.stack_advisor_backend.drools.data;

import com.stack_advisor.stack_advisor_backend.models.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LanguageRecommended {
    private Language language;
    private Double score;
}
