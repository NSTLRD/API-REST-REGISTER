package com.starlingdiaz.testapirestfullintegration;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(
		title = "API-REST-REGISTER",
		version = "1.0",
		description = "Securely manage user registrations and authentications. Provides JSON Web Tokens (JWT) on registration and login, which are required for subsequent authenticated requests."
))
public class TestApiRestfullIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApiRestfullIntegrationApplication.class, args);
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("public-api").pathsToMatch("/api/v1/users/**").build();
	}

}
