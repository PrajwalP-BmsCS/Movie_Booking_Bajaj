package com.cinema_package.cinema_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cinema_package.cinema_project.user.UserRepository;

@Configuration
@EnableMethodSecurity   // 👈 REQUIRED for @PreAuthorize
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter(UserRepository userRepository) {
        return new JwtFilter(userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UserRepository userRepository
    ) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // auth endpoints
                .requestMatchers("/auth/**").permitAll()
                
                // 🔓 Swagger / OpenAPI
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                // public movie browsing
                .requestMatchers(HttpMethod.GET, "/movie", "/movie/", "/movie/*","/movie/*/*").permitAll()


                // booking MUST be authenticated
                .requestMatchers("/movie/booking/**").authenticated()

                // everything else
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                jwtFilter(userRepository),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
