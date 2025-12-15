package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.RuleExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleExecutionLogRepository extends JpaRepository<RuleExecutionLog, Long> {
    List<RuleExecutionLog> findBySessionId(String sessionId);
}
