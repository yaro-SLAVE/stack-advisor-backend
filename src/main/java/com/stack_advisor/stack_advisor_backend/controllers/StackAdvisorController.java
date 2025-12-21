package com.stack_advisor.stack_advisor_backend.controllers;

import com.stack_advisor.stack_advisor_backend.dto.ExpertSystemResponseDTO;
import com.stack_advisor.stack_advisor_backend.dto.ProjectRequirementsDTO;
import com.stack_advisor.stack_advisor_backend.dto.TechnologyDTO;
import com.stack_advisor.stack_advisor_backend.dto.TechnologyRecommendationDTO;
import com.stack_advisor.stack_advisor_backend.models.ProjectRequirements;
import com.stack_advisor.stack_advisor_backend.models.TechnologyRecommendation;
import com.stack_advisor.stack_advisor_backend.services.StackAdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/stack-advisor")
@CrossOrigin(origins = "*")
public class StackAdvisorController {
    @Autowired
    private StackAdvisorService stackAdvisorService;

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeProject(@RequestBody ProjectRequirements requirements) {
        try {
            Map<String, Object> result = stackAdvisorService.analyzeProject(requirements);

            // Преобразуем в DTO
            ExpertSystemResponseDTO responseDTO = new ExpertSystemResponseDTO();
            responseDTO.setSessionId((String) result.get("sessionId"));
            responseDTO.setRulesFired((Integer) result.get("rulesFired"));
            responseDTO.setExplanationChain((List<String>) result.get("explanationChain"));
            responseDTO.setAuditLog((List<String>) result.get("auditLog"));

            if (result.get("requirements") instanceof ProjectRequirements) {
                responseDTO.setRequirements(
                        ProjectRequirementsDTO.fromEntity((ProjectRequirements) result.get("requirements"))
                );
            }

            if (result.get("recommendations") instanceof List) {
                List<?> recs = (List<?>) result.get("recommendations");
                responseDTO.setRecommendations(
                        recs.stream()
                                .filter(item -> item instanceof TechnologyRecommendation)
                                .map(item -> TechnologyRecommendationDTO.fromEntity((TechnologyRecommendation) item))
                                .collect(Collectors.toList())
                );
            }

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Ошибка при анализе проекта",
                    "message", e.getMessage(),
                    "timestamp", java.time.LocalDateTime.now()
            ));
        }
    }

    @GetMapping("/explanation/{sessionId}")
    public ResponseEntity<?> getExplanation(@PathVariable String sessionId) {
        try {
            return ResponseEntity.ok(stackAdvisorService.getExplanationBySession(sessionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Ошибка при получении объяснений",
                    "message", e.getMessage(),
                    "timestamp", java.time.LocalDateTime.now()
            ));
        }
    }

    @GetMapping("/recommendations/{sessionId}")
    public ResponseEntity<?> getRecommendations(@PathVariable String sessionId) {
        try {
            List<TechnologyRecommendation> recommendations =
                    stackAdvisorService.getRecommendationsBySession(sessionId);

            List<TechnologyRecommendationDTO> dtos = recommendations.stream()
                    .map(TechnologyRecommendationDTO::fromEntity)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Ошибка при получении рекомендаций",
                    "message", e.getMessage(),
                    "timestamp", java.time.LocalDateTime.now()
            ));
        }
    }

    @GetMapping("/technologies")
    public ResponseEntity<?> getAllTechnologies() {
        try {
            return ResponseEntity.ok(stackAdvisorService.getAllTechnologies().stream()
                    .map(TechnologyDTO::fromEntity)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Ошибка при получении технологий",
                    "message", e.getMessage(),
                    "timestamp", java.time.LocalDateTime.now()
            ));
        }
    }
}