package com.devashree.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingBackendApplication.class, args);
	}
}
