package com.stack_advisor.stack_advisor_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@Configuration
public class StackAdvisorBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackAdvisorBackendApplication.class, args);
	}

}
