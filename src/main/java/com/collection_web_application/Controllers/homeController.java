package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.CommentItem;
import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Entities.UserCollectionItems;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.CommentItemService;
import com.collection_web_application.Service.UserCollectionItemsService;
import com.collection_web_application.Service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class homeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCollectionItemsService userCollectionItemsService;
    @Autowired
    private CommentItemService commentItemService;

    @GetMapping("/")
    public String home(Model model) {

        List<UserCollectionItems> items = userCollectionItemsService.getAllItems();

        if (!items.isEmpty()) {
            model.addAttribute("item_view", items); // Pass the entire list to the view
            model.addAttribute("newComment", new CommentItem());

            return "home/home_page";
        } else {
            return "redirect:/error"; // Redirect to an error page if no items are found
        }
    }

    @PostMapping("/items/{itemId}/like")
    public String likeItem(@PathVariable Long itemId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        userCollectionItemsService.toggleLikeItem(itemId, user);
        return "redirect:/";
    }

    @PostMapping("/comments/add")
    public String addComment(@ModelAttribute CommentItem commentItem, @RequestParam("itemId") Long itemId, Principal principal) {

        UserCollectionItems item = userCollectionItemsService.getItemById(itemId).orElseThrow(() -> new IllegalArgumentException("Invalid item ID"));
        User user = userRepository.findByEmail(principal.getName());

        commentItem.setItem(item);
        commentItem.setUser(user);
        commentItemService.saveComment(commentItem);

        return "redirect:/"; // Redirect back to the home page
    }


}
