package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Entities.UserCollectionItems;
import com.collection_web_application.Repository.UserCollectionItemsRepository;
import com.collection_web_application.Repository.UserCollectionRepository;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserCollectionItemsService;
import com.collection_web_application.Service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserCollectionItemController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCollectionService userCollectionService;
    @Autowired
    private UserCollectionRepository userCollectionRepository;
    @Autowired
    private UserCollectionItemsRepository userCollectionItemsRepository;
    @Autowired
    private UserCollectionItemsService userCollectionItemsService;


    @GetMapping("/myCollection/item")
    public String itemList(@RequestParam("id") Long id, Model model, Principal principal) {

        //System.out.println("Received ID: " + id);  // Debugging line

        Optional<UserCollection> collectionOptional = userCollectionService.getCollectionById(id);
        User user = userRepository.findByEmail(principal.getName()); // Get the current user

        if (collectionOptional.isPresent()) {
            UserCollection collection = collectionOptional.get();
            List<UserCollectionItems> items = userCollectionItemsService.getItemsByCollectionAndUser(collection, user);

            // Debugging: Print items to ensure custom fields are included
            for (UserCollectionItems item : items) {
                System.out.println("Item ID: " + item.getId());
                System.out.println("Custom String Fields: " + item.getCustomStringFields());
                System.out.println("Custom Int Fields: " + item.getCustomIntFields());
            }

            //System.out.println("Found Collection: " + collection.getTitle());  // Debugging line
            model.addAttribute("collectionItem", collection);
            model.addAttribute("items", items);

        } else {
            //System.out.println("Collection not found!");  // Debugging line
            return "redirect:/error"; // or return a custom error page
        }

        return "user/item_page";
    }

    @GetMapping("/myCollection/viewItem")
    public String viewItem(@RequestParam("id") Long id, Model model, Principal principal) {

        // Retrieve the item based on the ID
        Optional<UserCollectionItems> itemOptional = userCollectionItemsRepository.findById(id);
        User user = userRepository.findByEmail(principal.getName()); // Get the current user

        if (itemOptional.isPresent() && user != null) {

            UserCollectionItems item = itemOptional.get();
            UserCollection collection = item.getUserCollection();

            // Ensure the item belongs to the user
            if (!collection.getUser().equals(user)) {
                return "redirect:/error"; // Redirect to an error page if the user does not own the item
            }

            // Add the item to the model
            model.addAttribute("item_view", item);
            model.addAttribute("collection_item", collection);

            return "user/view_item"; // Render the view_item page
        } else {
            return "redirect:/error"; // Redirect to an error page if the item is not found
        }
    }

    @PostMapping("/myCollection/addItem")
    public String addItem(@RequestParam("collectionId") Long collectionId, @RequestParam("name") String name,
                          @RequestParam("tag") String tag, @RequestParam Map<String, String> allParams, // All form parameters
                          Principal principal, RedirectAttributes redirectAttributes) {

        // Extract custom string and int fields manually
        Map<String, String> customStringFields = new HashMap<>();
        Map<String, String> customIntFields = new HashMap<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("customStringFields[")) {
                String key = entry.getKey().substring("customStringFields[".length(), entry.getKey().length() - 1); // Extract the field name
                customStringFields.put(key, entry.getValue());
            } else if (entry.getKey().startsWith("customIntFields[")) {
                String key = entry.getKey().substring("customIntFields[".length(), entry.getKey().length() - 1); // Extract the field name
                customIntFields.put(key, String.valueOf(Integer.parseInt(entry.getValue())));
            }
        }

        // Debugging: Print the correctly parsed maps
        System.out.println("Custom String Fields: " + customStringFields);
        System.out.println("Custom Int Fields: " + customIntFields);
        for (Map.Entry<String, String> entry : customIntFields.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        Optional<UserCollection> collectionOptional = userCollectionService.getCollectionById(collectionId);
        User user = userRepository.findByEmail(principal.getName());

        if (collectionOptional.isPresent() && user != null) {
            UserCollection userCollection = collectionOptional.get();

            UserCollectionItems item = new UserCollectionItems();
            item.setName(name);
            item.setTag(tag);
            item.setUserCollection(userCollection);
            item.setUser(user);

            // Populate item with custom fields
            item.setCustomStringFields(customStringFields);
            item.setCustomIntFields(customIntFields);

            userCollectionItemsRepository.save(item);
            redirectAttributes.addFlashAttribute("message", "Item added successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Collection or User not found.");
        }

        return "redirect:/user/myCollection/item?id=" + collectionId;
    }

    @PostMapping("/myCollection/editItem")
    public String editItem(@RequestParam("itemId") Long itemId, @RequestParam("collectionId") Long collectionId,
            @RequestParam("name") String name, @RequestParam("tag") String tag,
            @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        System.out.println("itemId: " + itemId);
        System.out.println("collectionId: " + collectionId);
        System.out.println("name: " + name);
        System.out.println("tag: " + tag);
        System.out.println("allParams: " + allParams);

        Optional<UserCollectionItems> itemOptional = userCollectionItemsRepository.findById(itemId);

        if (itemOptional.isPresent()) {
            UserCollectionItems item = itemOptional.get();
            item.setName(name);
            item.setTag(tag);

            // Extract and set custom fields
            Map<String, String> customStringFields = new HashMap<>();
            Map<String, String> customIntFields = new HashMap<>();

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (entry.getKey().startsWith("customStringFields[")) {
                    String key = entry.getKey().substring("customStringFields[".length(), entry.getKey().length() - 1);
                    customStringFields.put(key, entry.getValue());
                } else if (entry.getKey().startsWith("customIntFields[")) {
                    String key = entry.getKey().substring("customIntFields[".length(), entry.getKey().length() - 1);
                    try {
                        customIntFields.put(key, String.valueOf(Integer.parseInt(entry.getValue())));
                    } catch (NumberFormatException e) {
                        redirectAttributes.addFlashAttribute("error", "Invalid integer value for " + key);
                        return "redirect:/user/myCollection/item?id=" + collectionId;
                    }
                }
            }

            item.setCustomStringFields(customStringFields);
            item.setCustomIntFields(customIntFields);

            userCollectionItemsRepository.save(item);

            // Debug print to confirm the saved data
            System.out.println("Updated Custom String Fields: " + item.getCustomStringFields());
            System.out.println("Updated Custom Int Fields: " + item.getCustomIntFields());

            redirectAttributes.addFlashAttribute("message", "Item updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Item not found.");
        }

        return "redirect:/user/myCollection/item?id=" + collectionId;
    }


    @PostMapping("/myCollection/deleteItem")
    public String deleteItem(@RequestParam("itemId") Long itemId, @RequestParam("collectionId") Long collectionId,
                             RedirectAttributes redirectAttributes, Principal principal) {

        // Retrieve the currently logged-in user
        User user = userRepository.findByEmail(principal.getName());

        // Retrieve the item by ID and check if it belongs to a collection owned by the current user
        Optional<UserCollectionItems> itemOptional = userCollectionItemsRepository.findById(itemId);

        if (itemOptional.isPresent() && itemOptional.get().getUserCollection().getUser().equals(user)) {
            userCollectionItemsRepository.delete(itemOptional.get());
            redirectAttributes.addFlashAttribute("message", "Item deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Item not found or you do not have permission to delete it.");
        }

        return "redirect:/user/myCollection/item?id=" + collectionId;
    }


}
