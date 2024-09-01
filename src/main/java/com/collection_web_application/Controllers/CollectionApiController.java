package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionApiController {

    private final UserRepository userRepository;

    public CollectionApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   /* @GetMapping("/{apiToken}")
    public ResponseEntity<List<UserCollection>> getUserCollections(@PathVariable String apiToken) {
        User user = userRepository.findByApiToken(apiToken);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<UserCollection> collections = new ArrayList<>(user.getCollections());
        return ResponseEntity.ok(collections);
    }*/

    @GetMapping(value = "/{apiToken}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserCollection>> getUserCollections(@PathVariable String apiToken) {
        User user = userRepository.findByApiToken(apiToken);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<UserCollection> collections = new ArrayList<>(user.getCollections());
        return ResponseEntity.ok(collections);
    }


}
