package com.collection_web_application.Configurations;

import com.collection_web_application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Marks this class as a configuration class for Spring and enables Spring Security.
@Configuration
@EnableWebSecurity
public class MyConfig {

    // Autowire the UserService, CustomSuccessHandler, and CustomFailureHandler for use in security configuration.
    @Autowired
    private UserService userService;
    @Autowired
    private CustomSuccessHandler customSuccessHandler;
    @Autowired
    private CustomFailureHandler customFailureHandler;

    // Configures the security filter chain, which defines the security policies for the application.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Configure authorization rules for different URLs.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**", "/register", "/login").permitAll() // Allow access to these endpoints without authentication.
                        .requestMatchers("/user/**").hasRole("USER") // Restrict access to user-specific pages to users with the "USER" role.
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict access to admin-specific pages to users with the "ADMIN" role.
                        .anyRequest().authenticated())// Require authentication for all other requests.

                // Configure form-based login.
                .formLogin(form -> form
                        .loginPage("/login") // Specify the custom login page URL.
                        .permitAll() // Allow everyone to access the login page.
                        .successHandler(customSuccessHandler) // Use the custom success handler on successful login.
                        .failureHandler(customFailureHandler)) // Use the custom failure handler on failed login attempts.

                // Configure logout functionality.
                .logout(config -> config
                        .logoutSuccessUrl("/")) // Redirect to the homepage after a successful logout.

                // Configure session management.
                .sessionManagement(session -> session
                        .maximumSessions(1)  // Limit each user to one active session at a time.
                        .sessionRegistry(sessionRegistry())) // Attach the session registry to manage sessions.

                // Set the UserService as the user details service.
                .userDetailsService(userService)

                // Build and return the configured SecurityFilterChain.
                .build();
    }

    // Defines a bean for encoding passwords using BCrypt, a strong hashing function.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Defines a bean for managing sessions, allowing the application to keep track of active sessions.
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
