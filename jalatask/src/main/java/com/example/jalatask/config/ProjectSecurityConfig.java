package com.example.jalatask.config;

import com.example.jalatask.filter.JWTTokenGeneratorFilter;
import com.example.jalatask.filter.JWTTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

//        http.csrf(c->c.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        CorsConfiguration config = new CorsConfiguration();
//                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//                        config.setAllowedMethods(Collections.singletonList("*"));
//                        config.setAllowCredentials(true);
//                        config.setAllowedHeaders(Collections.singletonList("*"));
//                        config.setExposedHeaders(Arrays.asList("Authorization"));
//                        config.setMaxAge(3600L);
//                        return config;
//                    }
//                }))
//
//
//
//                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
//                .authorizeHttpRequests((requests)->requests
//                        .requestMatchers("/myAccount").hasRole("USER")
//                        .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
//                        .requestMatchers("/myLoans").hasRole("USER")
//                        .requestMatchers("/myCards").hasRole("USER")
//                        .requestMatchers("/user").authenticated()
//                        .requestMatchers("/notices","/contact","/register").permitAll())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize


                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Allow POST requests to /api/users without authentication
                        .requestMatchers(HttpMethod.GET, "/api/users/*").hasRole("USER") // Require role USER for GET requests to /api/users/*
                        .requestMatchers("/user").authenticated()



                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
