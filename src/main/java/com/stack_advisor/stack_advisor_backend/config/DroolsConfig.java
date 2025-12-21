package com.stack_advisor.stack_advisor_backend.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "src/main/resources/rules/";
    private final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieContainer kieContainer() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        Files.walk(Paths.get(RULES_PATH))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".drl"))
                .forEach(path -> {
                    String content;
                    try {
                        content = new String(Files.readAllBytes(path));
                        kieFileSystem.write(
                                "src/main/resources/" + path.getFileName().toString(),
                                kieServices.getResources().newByteArrayResource(content.getBytes())
                        );
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to read rule file: " + path, e);
                    }
                });

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    @Bean
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }
}
