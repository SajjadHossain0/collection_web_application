package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/collections")
public class CollectionApiController {

    private final UserRepository userRepository;

    @Autowired
    private UserCollectionService userCollectionService;

    public CollectionApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   /*@GetMapping(value = "/{apiToken}", produces = MediaType.APPLICATION_JSON_VALUE)
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

//
       List<UserCollection> userCollections = collections.stream()
               .map(collection -> {
                   UserCollection dto = new UserCollection();
                   dto = new UserCollection();
                   dto.setId(collection.getId());
                   dto.setTitle(collection.getTitle());
                   dto.setDescription(collection.getDescription());
                   dto.setNumberOfItems(collection.getItems() != null ? collection.getItems().size() : 0);
                   return dto;
               })
               .toList();
//

       return ResponseEntity.ok(collections);
   }


    @PostMapping("/import")
    public ResponseEntity<String> importCollections(@RequestParam String apiToken) {
        String result =
                userCollectionService.importCollections(apiToken);
        if (result.contains("Error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }

}
