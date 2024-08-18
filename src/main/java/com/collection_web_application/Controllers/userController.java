package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Repository.UserCollectionRepository;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class userController {
    //Authenticated users page

    @Autowired
    private UserCollectionRepository userCollectionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCollectionService userCollectionService;


    @GetMapping("")
    public String user(Model model, Principal principal) {

        // Get the currently logged-in user
        User user = userRepository.findByEmail(principal.getName());
        // send order to html page to show data
        model.addAttribute("userCollection", userCollectionService.getCollectionsByUser(user));

        return "user/user_page";
    }

    /*
    @PostMapping("/add_collection")
    public String addCollection(@RequestParam("title") String title,
                                @RequestParam("description") String description,
                                Principal principal){

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

        userCollectionRepository.save(userCollection);

        return "redirect:/user"; // Redirect to a list of collections or another page
    }
    */

    @PostMapping("/add_collection")
    public String addCollection(@RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("custom_string1_state") Optional<Boolean> customString1State,
                                @RequestParam("custom_string1_name") Optional<String> customString1Name,
                                @RequestParam("custom_string2_state") Optional<Boolean> customString2State,
                                @RequestParam("custom_string2_name") Optional<String> customString2Name,
                                @RequestParam("custom_string3_state") Optional<Boolean> customString3State,
                                @RequestParam("custom_string3_name") Optional<String> customString3Name,
                                @RequestParam("custom_int1_state") Optional<Boolean> customInt1State,
                                @RequestParam("custom_int1_name") Optional<String> customInt1Name,
                                @RequestParam("custom_int2_state") Optional<Boolean> customInt2State,
                                @RequestParam("custom_int2_name") Optional<String> customInt2Name,
                                @RequestParam("custom_int3_state") Optional<Boolean> customInt3State,
                                @RequestParam("custom_int3_name") Optional<String> customInt3Name,
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
            @RequestParam("collectionId") Long collectionId,
            Principal principal) {

        // Get the currently logged-in user
        User user = userRepository.findByEmail(principal.getName());

        // Retrieve the collection by ID and check if it belongs to the current user
        Optional<UserCollection> collection = userCollectionRepository.findById(collectionId);

        if (collection.isPresent() && collection.get().getUser().equals(user)) {
            userCollectionRepository.delete(collection.get());
        }

        return "redirect:/user"; // Redirect to the user's collection page
    }

/*    @PostMapping("/edit_collection")
    public String editCollection(
            @RequestParam("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description){

        Optional<UserCollection> optionalCollection = userCollectionRepository.findById(id);

        if (optionalCollection.isPresent()) {
            UserCollection editedUserCollection = optionalCollection.get();
            editedUserCollection.setTitle(title);
            editedUserCollection.setDescription(description);

            userCollectionRepository.save(editedUserCollection);
        }

        return "redirect:/user";
    }*/

    @PostMapping("/edit_collection")
    public String editCollection(
            @RequestParam("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("custom_string1_state") Optional<Boolean> customString1State,
            @RequestParam("custom_string1_name") Optional<String> customString1Name,
            @RequestParam("custom_string2_state") Optional<Boolean> customString2State,
            @RequestParam("custom_string2_name") Optional<String> customString2Name,
            @RequestParam("custom_string3_state") Optional<Boolean> customString3State,
            @RequestParam("custom_string3_name") Optional<String> customString3Name,
            @RequestParam("custom_int1_state") Optional<Boolean> customInt1State,
            @RequestParam("custom_int1_name") Optional<String> customInt1Name,
            @RequestParam("custom_int2_state") Optional<Boolean> customInt2State,
            @RequestParam("custom_int2_name") Optional<String> customInt2Name,
            @RequestParam("custom_int3_state") Optional<Boolean> customInt3State,
            @RequestParam("custom_int3_name") Optional<String> customInt3Name){

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

    @GetMapping("/myCollection/item")
    public String itemList(@RequestParam("id") Long id,
                           Model model) {

        //System.out.println("Received ID: " + id);  // Debugging line

        Optional<UserCollection> collectionOptional = userCollectionService.getCollectionById(id);

        if (collectionOptional.isPresent()) {
            UserCollection collection = collectionOptional.get();
            //System.out.println("Found Collection: " + collection.getTitle());  // Debugging line
            model.addAttribute("collectionItem", collection);
        } else {
            //System.out.println("Collection not found!");  // Debugging line
            return "redirect:/error"; // or return a custom error page
        }

        return "user/item_page";
    }


}
