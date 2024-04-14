/**
 * @author Starling Diaz on 4/13/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/13/2024.
 */

package com.starlingdiaz.testapirestfullintegration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("test")
@Configuration
@EnableWebSecurity
public class SecurityConfigTest {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF correctamente
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()); // Permite todas las solicitudes
        return http.build();
    }
}

