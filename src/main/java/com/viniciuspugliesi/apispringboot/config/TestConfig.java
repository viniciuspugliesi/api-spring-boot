package com.viniciuspugliesi.apispringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

	@Bean
	public boolean test() {
		System.out.println("Testando profile test");
		return true;
	}
}
