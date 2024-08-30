package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Repository.UserCollectionRepository;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserCollectionService;
import com.collection_web_application.Service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserCollectionController {

    @Autowired
    private UserCollectionRepository userCollectionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCollectionService userCollectionService;
    @Autowired
    private UserDataService userDataService;


    @PostMapping("/add_collection")
    public String addCollection(@RequestParam("title") String title, @RequestParam("description") String description,
                                @RequestParam("custom_string1_state") Optional<Boolean> customString1State, @RequestParam("custom_string1_name") Optional<String> customString1Name,
                                @RequestParam("custom_string2_state") Optional<Boolean> customString2State, @RequestParam("custom_string2_name") Optional<String> customString2Name,
                                @RequestParam("custom_string3_state") Optional<Boolean> customString3State, @RequestParam("custom_string3_name") Optional<String> customString3Name,
                                @RequestParam("custom_int1_state") Optional<Boolean> customInt1State, @RequestParam("custom_int1_name") Optional<String> customInt1Name,
                                @RequestParam("custom_int2_state") Optional<Boolean> customInt2State, @RequestParam("custom_int2_name") Optional<String> customInt2Name,
                                @RequestParam("custom_int3_state") Optional<Boolean> customInt3State, @RequestParam("custom_int3_name") Optional<String> customInt3Name,
                                Principal principal)throws NullPointerException{

        UserCollection userCollection = new UserCollection();
        userCollection.setTitle(title);
        userCollection.setDescription(description);

        // Format the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd MMM yyyy");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        userCollection.setCollection_creation_date(formattedDateTime);

        // Retrieve the user based on the principal's username
        User user = userRepository.findByEmail(principal.getName());
        userCollection.setUser(user);

        // Setting custom fields
        userCollection.setCustom_string1_state(customString1State.orElse(false));
        userCollection.setCustom_string1_name(customString1Name.orElse(null));
        userCollection.setCustom_string2_state(customString2State.orElse(false));
        userCollection.setCustom_string2_name(customString2Name.orElse(null));
        userCollection.setCustom_string3_state(customString3State.orElse(false));
        userCollection.setCustom_string3_name(customString3Name.orElse(null));


        userCollection.setCustom_int1_state(customInt1State.orElse(false));
        userCollection.setCustom_int1_name(customInt1Name.orElse(null));
        userCollection.setCustom_int2_state(customInt2State.orElse(false));
        userCollection.setCustom_int2_name(customInt2Name.orElse(null));
        userCollection.setCustom_int3_state(customInt3State.orElse(false));
        userCollection.setCustom_int3_name(customInt3Name.orElse(null));


        userCollectionRepository.save(userCollection);
        return "redirect:/user"; // Redirect to a list of collections or another page
    }

    @PostMapping("/collection/delete")
    public String deleteCollection(
            @RequestParam("collectionId") Long collectionId, Principal principal) {

        // Get the currently logged-in user
        User user = userRepository.findByEmail(principal.getName());

        // Retrieve the collection by ID and check if it belongs to the current user
        Optional<UserCollection> collection = userCollectionRepository.findById(collectionId);

        if (collection.isPresent() && collection.get().getUser().equals(user)) {
            userCollectionRepository.delete(collection.get());
        }

        return "redirect:/user"; // Redirect to the user's collection page
    }


    @PostMapping("/edit_collection")
    public String editCollection(
            @RequestParam("id") Long id, @RequestParam("title") String title, @RequestParam("description") String description,
            @RequestParam("custom_string1_state") Optional<Boolean> customString1State, @RequestParam("custom_string1_name") Optional<String> customString1Name,
            @RequestParam("custom_string2_state") Optional<Boolean> customString2State, @RequestParam("custom_string2_name") Optional<String> customString2Name,
            @RequestParam("custom_string3_state") Optional<Boolean> customString3State, @RequestParam("custom_string3_name") Optional<String> customString3Name,
            @RequestParam("custom_int1_state") Optional<Boolean> customInt1State, @RequestParam("custom_int1_name") Optional<String> customInt1Name,
            @RequestParam("custom_int2_state") Optional<Boolean> customInt2State, @RequestParam("custom_int2_name") Optional<String> customInt2Name,
            @RequestParam("custom_int3_state") Optional<Boolean> customInt3State, @RequestParam("custom_int3_name") Optional<String> customInt3Name){


        System.out.println("Editing Collection with ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);

        Optional<UserCollection> optionalCollection = userCollectionRepository.findById(id);

        if (optionalCollection.isPresent()) {

            UserCollection editedUserCollection = optionalCollection.get();

            editedUserCollection.setTitle(title);
            editedUserCollection.setDescription(description);
            // Setting custom fields
            editedUserCollection.setCustom_string1_state(customString1State.orElse(false));
            editedUserCollection.setCustom_string1_name(customString1Name.orElse(null));
            editedUserCollection.setCustom_string2_state(customString2State.orElse(false));
            editedUserCollection.setCustom_string2_name(customString2Name.orElse(null));
            editedUserCollection.setCustom_string3_state(customString3State.orElse(false));
            editedUserCollection.setCustom_string3_name(customString3Name.orElse(null));
            //==========
            editedUserCollection.setCustom_int1_state(customInt1State.orElse(false));
            editedUserCollection.setCustom_int1_name(customInt1Name.orElse(null));
            editedUserCollection.setCustom_int2_state(customInt2State.orElse(false));
            editedUserCollection.setCustom_int2_name(customInt2Name.orElse(null));
            editedUserCollection.setCustom_int3_state(customInt3State.orElse(false));
            editedUserCollection.setCustom_int3_name(customInt3Name.orElse(null));

            userCollectionRepository.save(editedUserCollection);
        }

        return "redirect:/user";
    }


    @GetMapping("/collections")
    public ResponseEntity<List<UserCollection>> getUserCollections(
            @RequestParam String apiToken) {
        User user = userDataService.findUserByToken(apiToken);
        if (user != null) {
            List<UserCollection> collections = userCollectionService.getCollectionsByUser(user);
            return ResponseEntity.ok(collections);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
