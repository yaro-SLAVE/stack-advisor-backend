package com.stack_advisor.stack_advisor_backend.models;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
public class RecommendedStack {
    private ProjectRequirements requirements;
    private List<TechnologyStack> recommendedStacks = new ArrayList<>();
    private TechnologyStack primaryRecommendation;

    public void addRecommendedStack(TechnologyStack stack) {
        this.recommendedStacks.add(stack);
    }
}
