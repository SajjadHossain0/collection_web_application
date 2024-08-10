package com.collection_web_application.ServiceImplements;


import com.collection_web_application.Entities.User;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDataServiceImpl implements UserDataService {

    @Autowired
    private UserRepository userRepository;

    public UserDataServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByID(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == null) {
            // New user, check email uniqueness
            if (isEmailTaken(user.getEmail())) {
                throw new RuntimeException("Email already exists!");
            }
        } else {
            // Update existing user, email check can be skipped if the email has not changed
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null && !existingUser.getEmail().equals(user.getEmail()) && isEmailTaken(user.getEmail())) {
                throw new RuntimeException("Email already exists!");
            }
        }
        userRepository.save(user);
    }

    private boolean isEmailTaken(String email) {
        Optional<User> userOpt = Optional.ofNullable(userRepository.findByEmail(email));
        return userOpt.isPresent() && userOpt.get().isActive();
    }


    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> searchUserByEmailOrRole(String query) {
        try {
            // Extract email and role from the query string
            // For simplicity, let's assume the query string is formatted as "email:role"
            String[] parts = query.split(":");
            String email = parts.length > 0 ? parts[0] : "";
            String role = parts.length > 1 ? parts[1] : "";

            // Use the findByEmailOrRole method to search for users
            return userRepository.findByEmailOrRole(email, role);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur
            return Collections.emptyList();
        }
    }


}