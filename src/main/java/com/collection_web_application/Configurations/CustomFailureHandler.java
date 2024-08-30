package com.collection_web_application.Configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

// Marks this class as a Spring component, making it eligible for component scanning and dependency injection.
@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException{

        // Check if the exception message is "User is blocked".
        if (exception.getMessage().equals("User is blocked")) {

            // If the user is blocked, redirect to the login page with a specific error parameter indicating the user is blocked.
            response.sendRedirect("/login?error=blocked");

        } else {
            // For any other type of authentication failure, redirect to the login page with a generic error parameter.
            response.sendRedirect("/login?error=true");
        }
    }
}