package com.stack_advisor.stack_advisor_backend.services;

import com.stack_advisor.stack_advisor_backend.models.Framework;
import com.stack_advisor.stack_advisor_backend.models.Language;
import com.stack_advisor.stack_advisor_backend.models.enums.framework.TasksType;
import com.stack_advisor.stack_advisor_backend.repositories.FrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FrameworkService {
    @Autowired
    private FrameworkRepository frameworkRepository;

    public List<Framework> getAllFrameworks() { return frameworkRepository.findAll(); }

    public Framework createFramework(String name, List<Language> languages, Boolean isReactive, LocalDate lastUpdatedAt, TasksType tasksType) {
        Framework framework = new Framework(
                name,
                languages,
                isReactive,
                lastUpdatedAt,
                tasksType
        );

        framework = frameworkRepository.save(framework);
        return framework;
    }
}
