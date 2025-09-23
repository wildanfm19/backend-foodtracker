package com.erlin.foodtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class FoodtrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodtrackerApplication.class, args);
	}

}
