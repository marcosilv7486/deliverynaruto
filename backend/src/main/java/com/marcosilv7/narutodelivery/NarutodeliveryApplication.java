package com.marcosilv7.narutodelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(
		basePackageClasses = { NarutodeliveryApplication.class, Jsr310JpaConverters.class }
)
@PropertySource("classpath:custom.properties")
public class NarutodeliveryApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NarutodeliveryApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(NarutodeliveryApplication.class, args);
	}
}
