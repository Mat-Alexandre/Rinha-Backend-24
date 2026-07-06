package com.rinha.backend2024;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Backend2024Application {

	public static void main(String[] args) {
		SpringApplication.run(Backend2024Application.class, args);
	}

}
