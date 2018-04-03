package com.marcosilv7.narutodelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:custom.properties")
public class NarutodeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(NarutodeliveryApplication.class, args);
	}
}
