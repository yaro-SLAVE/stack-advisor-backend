package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.ProjectRequirements;
import com.stack_advisor.stack_advisor_backend.models.Session;
import com.stack_advisor.stack_advisor_backend.models.TechnologyRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnologyRecommendationRepository extends JpaRepository<TechnologyRecommendation, Integer> {
    List<TechnologyRecommendation> findByRequirements(ProjectRequirements requirements);
}
