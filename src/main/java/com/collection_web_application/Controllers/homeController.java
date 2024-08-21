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


    /*@GetMapping("/")
    public String home(Model model) {

        List<UserCollectionItems> itemsOptional = userCollectionItemsService.getAllItems();

        if (!itemsOptional.isEmpty()){

                UserCollectionItems item = itemsOptional.get(0);
                UserCollection collection = item.getUserCollection();

                // Add the item to the model
                model.addAttribute("item_view", item);
                model.addAttribute("collection_item", collection);

            return "home/home_page";
        }
        else {
            return "redirect:/error"; // Redirect to an error page if the item is not found
        }
    }*/

        @GetMapping("/")
        public String home(Model model) {

            List<UserCollectionItems> items = userCollectionItemsService.getAllItems();

            if (!items.isEmpty()) {
                model.addAttribute("item_view", items); // Pass the entire list to the view
                return "home/home_page";
            } else {
                return "redirect:/error"; // Redirect to an error page if no items are found
            }
        }


}
