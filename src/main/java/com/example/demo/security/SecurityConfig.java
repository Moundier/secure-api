package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.auth.AuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthFilter authFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {

        System.out.println("Filter starts");

        return http
        .csrf((csrf) -> csrf
            .disable()
        )
        .securityMatchers((matchers) -> matchers
        // Which ones must be secured
            .requestMatchers("/auth/**")
            .requestMatchers("/demo")
            .requestMatchers("/course/**")
        )
        .authorizeHttpRequests((authz) -> authz
        // How we secure those ones

            // Can POST 
            .requestMatchers(HttpMethod.POST ,"/auth/**").permitAll()
            // Can POST ROLE_ADMIN
            .requestMatchers(HttpMethod.POST, "/course/**").hasAuthority("ADMIN")
            // Only authenticated (ROLE_USER, ROLE_ADMIN)
            .anyRequest().authenticated()
        )
        .sessionManagement((session) -> session
            .sessionCreationPolicy(STATELESS)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }
}