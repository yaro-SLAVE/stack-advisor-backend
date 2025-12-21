package com.stack_advisor.stack_advisor_backend.components;

import com.stack_advisor.stack_advisor_backend.models.Technology;
import com.stack_advisor.stack_advisor_backend.repositories.TechnologyRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initData(TechnologyRepository technologyRepository) {
        return args -> {
            if (technologyRepository.count() == 0) {
                // Backend technologies
                Technology springBoot = createTechnology(
                        "Spring Boot",
                        Technology.TechnologyCategory.BACKEND,
                        "Java фреймворк для создания микросервисов",
                        0.7, 0.9, 0.95, 0.9, 0.85,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Корпоративные приложения, микросервисы"
                );
                technologyRepository.save(springBoot);

                Technology django = createTechnology(
                        "Django",
                        Technology.TechnologyCategory.BACKEND,
                        "Python фреймворк для быстрой разработки",
                        0.6, 0.8, 0.85, 0.9, 0.75,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Быстрое прототипирование, CMS"
                );
                technologyRepository.save(django);

                Technology nodejs = createTechnology(
                        "Node.js",
                        Technology.TechnologyCategory.BACKEND,
                        "JavaScript runtime на движке V8",
                        0.5, 0.9, 0.95, 0.85, 0.9,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Real-time приложения, API"
                );
                technologyRepository.save(nodejs);

                // Frontend technologies
                Technology react = createTechnology(
                        "React",
                        Technology.TechnologyCategory.FRONTEND,
                        "JavaScript библиотека для создания UI",
                        0.6, 0.8, 0.95, 0.9, 0.85,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "SPA, сложные интерфейсы"
                );
                technologyRepository.save(react);

                Technology vuejs = createTechnology(
                        "Vue.js",
                        Technology.TechnologyCategory.FRONTEND,
                        "Прогрессивный JavaScript фреймворк",
                        0.4, 0.7, 0.8, 0.8, 0.8,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Быстрая разработка, небольшие проекты"
                );
                technologyRepository.save(vuejs);

                Technology angular = createTechnology(
                        "Angular",
                        Technology.TechnologyCategory.FRONTEND,
                        "TypeScript фреймворк для enterprise приложений",
                        0.8, 0.7, 0.85, 0.9, 0.8,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Крупные enterprise приложения"
                );
                technologyRepository.save(angular);

                // Database technologies
                Technology postgresql = createTechnology(
                        "PostgreSQL",
                        Technology.TechnologyCategory.DATABASE,
                        "Объектно-реляционная СУБД",
                        0.7, 0.85, 0.85, 0.95, 0.8,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Сложные транзакции, данные с отношениями"
                );
                technologyRepository.save(postgresql);

                Technology mongodb = createTechnology(
                        "MongoDB",
                        Technology.TechnologyCategory.DATABASE,
                        "Документоориентированная NoSQL СУБД",
                        0.5, 0.9, 0.9, 0.85, 0.85,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Гибкая схема данных, JSON документы"
                );
                technologyRepository.save(mongodb);

                Technology redis = createTechnology(
                        "Redis",
                        Technology.TechnologyCategory.DATABASE,
                        "In-memory data structure store",
                        0.4, 0.95, 0.85, 0.9, 0.95,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Кэширование, real-time приложения"
                );
                technologyRepository.save(redis);

                // DevOps technologies
                Technology docker = createTechnology(
                        "Docker",
                        Technology.TechnologyCategory.DEVOPS,
                        "Платформа для контейнеризации",
                        0.6, 0.9, 0.95, 0.9, 0.9,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Контейнеризация приложений"
                );
                technologyRepository.save(docker);

                Technology kubernetes = createTechnology(
                        "Kubernetes",
                        Technology.TechnologyCategory.DEVOPS,
                        "Оркестратор контейнеров",
                        0.9, 0.95, 0.9, 0.85, 0.9,
                        Technology.LicenseType.OPEN_SOURCE,
                        true, true,
                        "Управление микросервисами"
                );
                technologyRepository.save(kubernetes);

                System.out.println("Initialized " + technologyRepository.count() + " technologies");
            }
        };
    }

    private Technology createTechnology(
            String name,
            Technology.TechnologyCategory category,
            String description,
            Double complexity,
            Double scalability,
            Double communitySize,
            Double maturity,
            Double performance,
            Technology.LicenseType license,
            Boolean cloudNative,
            Boolean microservicesReady,
            String bestFor) {

        Technology tech = new Technology();
        tech.setName(name);
        tech.setCategory(category);
        tech.setDescription(description);
        tech.setComplexity(complexity);
        tech.setScalability(scalability);
        tech.setCommunitySize(communitySize);
        tech.setMaturity(maturity);
        tech.setPerformance(performance);
        tech.setLicense(license);
        tech.setCloudNative(cloudNative);
        tech.setMicroservicesReady(microservicesReady);
        tech.setBestFor(bestFor);

        return tech;
    }
}
