package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Integer> {
}
