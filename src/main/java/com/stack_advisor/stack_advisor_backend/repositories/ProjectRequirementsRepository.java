package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.ProjectRequirements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRequirementsRepository extends JpaRepository<ProjectRequirements, Integer> {
}
