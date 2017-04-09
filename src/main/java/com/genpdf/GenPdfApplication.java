package com.genpdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class GenPdfApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenPdfApplication.class, args);
	}
}
