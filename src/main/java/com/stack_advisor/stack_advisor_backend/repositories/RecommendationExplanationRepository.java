package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.RecommendationExplanation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationExplanationRepository extends JpaRepository<RecommendationExplanation, Long> {
    List<RecommendationExplanation> findBySessionId(String sessionId);
    List<RecommendationExplanation> findBySessionIdAndRecommendationType(String sessionId, String recommendationType);
}
