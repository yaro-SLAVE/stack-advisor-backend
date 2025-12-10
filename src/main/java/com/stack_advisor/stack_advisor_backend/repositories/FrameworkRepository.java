package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.Framework;
import com.stack_advisor.stack_advisor_backend.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrameworkRepository extends JpaRepository<Framework, Long> {
    List<Framework> findByNameIn(List<String> names);
    List<Framework> findByLanguagesContaining(Language language);
}
