package com.service.hydrometrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaRepositories
@EnableEnversRepositories
@CrossOrigin(origins = "*", maxAge = 3600)
public class HydrometricsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HydrometricsApplication.class, args);
	}
}
