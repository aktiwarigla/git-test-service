package com.gla.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GitTestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitTestServiceApplication.class, args);
	}
	
	@GetMapping
	public String greet() {
		return "git test ";
	}

}
