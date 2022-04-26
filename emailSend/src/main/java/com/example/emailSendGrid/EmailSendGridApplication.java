package com.example.emailSendGrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmailSendGridApplication {
	private static final Logger logger = LoggerFactory.getLogger(EmailSendGridApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(EmailSendGridApplication.class, args);
	}
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			MDC.put("events", "Application Startup");
			logger.info("Starting...");
			logger.info("Complete");
			MDC.clear();
		};
	}

}
