package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.CommentItem;
import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Entities.UserCollectionItems;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.CommentItemService;
import com.collection_web_application.Service.UserCollectionItemsService;
import com.collection_web_application.Service.UserCollectionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@Controller
public class homeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCollectionService userCollectionService;

    @Autowired
    private UserCollectionItemsService userCollectionItemsService;

    @Autowired
    private CommentItemService commentItemService;

    // Handles GET requests to the home page
    @GetMapping("/")
    public String home(Model model) {

        List<UserCollectionItems> items = userCollectionItemsService.getAllItems();

        if (!items.isEmpty()) {
            model.addAttribute("item_view", items); // Passes the list of items to the view
            model.addAttribute("newComment", new CommentItem()); // Prepares a new CommentItem object for the comment form
            return "home/home_page"; // Returns the home page view
        } else {
            return "redirect:/error"; // Redirects to an error page if no items are found
        }
    }

    // Handles the liking of items
    @PostMapping("/items/{itemId}/like")
    public String likeItem(@PathVariable Long itemId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()); // Gets the currently logged-in user
        userCollectionItemsService.toggleLikeItem(itemId, user); // Toggles the like status for the item
        return "redirect:/"; // Redirects back to the home page
    }

    // Handles the addition of comments to items
    @PostMapping("/comments/add")
    public String addComment(@ModelAttribute CommentItem commentItem, @RequestParam("itemId") Long itemId, Principal principal) {
        UserCollectionItems item = userCollectionItemsService.getItemById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID")); // Fetches the item or throws an exception if not found
        User user = userRepository.findByEmail(principal.getName()); // Gets the currently logged-in user

        commentItem.setItem(item); // Associates the comment with the item
        commentItem.setUser(user); // Associates the comment with the user
        commentItemService.saveComment(commentItem); // Saves the comment

        return "redirect:/"; // Redirects back to the home page
    }


}
