package com.devashree.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TicketingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingBackendApplication.class, args);
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		System.out.println(encoder.encode("pass123"));
	}
}
