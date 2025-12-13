package com.cinema_package.cinema_project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // auth endpoints
                .requestMatchers("/auth/**").permitAll()

                // public movie browsing
                .requestMatchers("/movie").permitAll()
                .requestMatchers("/movie/").permitAll()
                .requestMatchers("/movie/*").permitAll()

                // booking MUST be authenticated
                .requestMatchers("/movie/booking/**").authenticated()

                // everything else
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                new JwtFilter(),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
