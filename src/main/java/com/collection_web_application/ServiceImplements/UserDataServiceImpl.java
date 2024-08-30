package com.collection_web_application.ServiceImplements;


import com.collection_web_application.Entities.User;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public User findUserByToken(String apiToken) {
        // Find user by API token
        return userRepository.findByApiToken(apiToken);
    }

    public String generateApiToken(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // Generate a token (e.g., UUID)
        String token = UUID.randomUUID().toString();
        user.setApiToken(token); // Save the token to the user record
        userRepository.save(user);
        return token;
    }

}