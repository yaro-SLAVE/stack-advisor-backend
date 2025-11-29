package com.stack_advisor.stack_advisor_backend.models;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
public class TechnologyStack {
    private String stackName;
    private List<String> backendFrameworks = new ArrayList<>();
    private List<String> frontendFrameworks = new ArrayList<>();
    private List<String> databases = new ArrayList<>();
    private List<String> messagingSystems = new ArrayList<>();
    private List<String> cloudProviders = new ArrayList<>();
    private List<String> devOpsTools = new ArrayList<>();
    private List<String> testingFrameworks = new ArrayList<>();
    private String architectureStyle;
    private String deploymentStrategy;
    private int confidenceScore;
    private String recommendationReason;

    public void addBackendFramework(String framework) {
        this.backendFrameworks.add(framework);
    }

    public void addFrontendFramework(String framework) {
        this.frontendFrameworks.add(framework);
    }

    public void addDatabase(String database) {
        this.databases.add(database);
    }

    public void addDevOpsTool(String tool) {
        this.devOpsTools.add(tool);
    }

    public void addTestingFramework(String framework) {
        this.testingFrameworks.add(framework);
    }

    public void addMessagingSystem(String messagingSystem) {
        this.messagingSystems.add(messagingSystem);
    }

    public void addCloudProvider(String cloudProvider) {
        this.cloudProviders.add(cloudProvider);
    }
}
