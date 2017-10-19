package com.phshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.ph","com.phshopping"})
public class PhshoppingApiPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhshoppingApiPayApplication.class, args);
	}
}
