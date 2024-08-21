package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Entities.UserCollectionItems;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserCollectionItemsService;
import com.collection_web_application.Service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class homeController {

    @Autowired
    private UserCollectionService userCollectionService;
    @Autowired
    private UserCollectionItemsService userCollectionItemsService;
    @Autowired
    private UserRepository userRepository;


/*    @GetMapping("/")
    public String home(Model model) {

        // Retrieve all collections
        List<UserCollection> collections = userCollectionService.getAllCollections();

        // Create a map to hold collection and their corresponding items
        Map<UserCollection, List<UserCollectionItems>> collectionItemsMap = new HashMap<>();

        for (UserCollection collection : collections) {
            // Get all items for each collection
            List<UserCollectionItems> items = userCollectionItemsService.getItemsByCollection(collection);

            // Add the collection and its items to the map
            collectionItemsMap.put(collection, items);
        }

        // Add the map to the model
        model.addAttribute("collectionItemsMap", collectionItemsMap);

        return "home/home_page";
    }*/

    @GetMapping("/")
    public String home(Model model) {

            return "home/home_page"; // The view to render

    }







}
