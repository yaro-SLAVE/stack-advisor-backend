package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.RuleExecutionLog;
import com.stack_advisor.stack_advisor_backend.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleExecutionLogRepository extends JpaRepository<RuleExecutionLog, Integer> {
    List<RuleExecutionLog> findBySession(Session session);
}
