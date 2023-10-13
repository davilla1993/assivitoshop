package com.gbossoufolly.assivitoshopbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {
    private final JWTRequestFilter jwtRequestFilter;
    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http.
                csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .addFilterBefore(jwtRequestFilter, AuthorizationFilter.class)
               .authorizeHttpRequests(auth ->
                       auth.requestMatchers("/product", "/auth/register", "/auth/login", "/auth/verify",
                                       "/auth/forgot", "/auth/reset","/error",
                                       "/websocket", "/websocket/**").permitAll()
                       .anyRequest().authenticated())
               .build();
    }
}
