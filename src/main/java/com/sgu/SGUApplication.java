package com.sgu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SGUApplication {

	public static void main(String[] args) {
		SpringApplication.run(SGUApplication.class, args);
	}

}
