package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class searchController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/userDetails/search/{query}")
    public ResponseEntity<List<User>> searchUser(@PathVariable("query") String query, Principal principal) {
        List<User> users = userRepository.findByEmailOrRoleContaining(query);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

}
