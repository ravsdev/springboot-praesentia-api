package com.praesentia.praesentiaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PraesentiaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PraesentiaApiApplication.class, args);
	}
}
