package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String registerForm(Model model) {
        User registeredUser = new User();
        model.addAttribute("registeredUser", registeredUser);
        model.addAttribute("success", false);
        model.addAttribute("error", false);
        return "authentication/register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @Valid @ModelAttribute("registeredUser") User registeredUser, BindingResult result) {

        // Check if the entered email has already been used
        User userForEmailCheck = userRepository.findByEmail(registeredUser.getEmail());
        if (userForEmailCheck != null) {
            result.addError(new FieldError(
                    "registeredUser",
                    "email",
                    "Email Address is already used. Try another..."));
        }

        // If any error occurs then it will redirect to the register page
        if (result.hasErrors()) {
            model.addAttribute("error", true);
            return "authentication/register";
        }

        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            // Format the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd MMM yyyy");
            String formattedDateTime = LocalDateTime.now().format(formatter);

            User newUser = new User();
            newUser.setName(registeredUser.getName());
            newUser.setEmail(registeredUser.getEmail());
            newUser.setPassword(bCryptPasswordEncoder.encode(registeredUser.getPassword()));
            newUser.setLastLoginTime(formattedDateTime);
            newUser.setRegistrationTime(formattedDateTime);
            newUser.setRole("USER");

            userRepository.save(newUser);

            model.addAttribute("registeredUser", new User());
            model.addAttribute("success", true);

        } catch (Exception e) {
            result.rejectValue("name", "error.registeredUser", e.getMessage()); // Show the error to name field
        }

        return "authentication/register";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "authentication/login";
    }

}