package com.collection_web_application.Configurations;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Configuration
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // this is for admin access
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/user");
        } else {
            response.sendRedirect("/");

        }


        // Update the last login time
        String email = authentication.getName(); // Get the authenticated user's email
        User user = userRepository.findByEmail(email);
        if (user != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd MMM yyyy");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            user.setLastLoginTime(formattedDateTime);
            userRepository.save(user);
        }
    }
}