package com.ratelimiting.RateLimitSolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.ratelimiting"})
public class RateLimitSolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RateLimitSolutionApplication.class, args);
	}

}
