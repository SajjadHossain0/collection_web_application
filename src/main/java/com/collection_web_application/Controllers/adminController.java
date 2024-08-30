package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Entities.UserCollectionItems;
import com.collection_web_application.Repository.UserCollectionItemsRepository;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserCollectionItemsService;
import com.collection_web_application.Service.UserCollectionService;
import com.collection_web_application.Service.UserDataService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

// Marks this class as a Spring MVC controller and maps the base URL for the controller's methods to "/admin".
@Controller
@RequestMapping("/admin")
public class adminController {

    // Autowire the necessary services and session registry for use in this controller.
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private UserCollectionService userCollectionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCollectionItemsService userCollectionItemsService;
    @Autowired
    private UserCollectionItemsRepository userCollectionItemsRepository;

    // Handles the request for the main admin page.
    @GetMapping("")
    public String admin(Model model) {
        model.addAttribute("userDetails", userDataService.getAllUsers()); // Add all users to the model for display on the admin page.
        return "admin/admin_page"; // Return the admin page view.
    }

    // Handles requests to view a specific user's details.
    @GetMapping("/user/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model) {
        User user = userDataService.getUserByID(id); // Retrieve the user by ID.
        if (user == null) {
            return "redirect:/admin"; // Redirect to the admin page if the user is not found.
        }
        model.addAttribute("user", user); // Add the user to the model.
        List<UserCollection> collectionsByUser = userCollectionService.getCollectionsByUser(user); // Retrieve the user's collections.
        model.addAttribute("userCollection", collectionsByUser); // Add the collections to the model.
        return "admin/viewUserCollection"; // Return the user page view.
    }

    /*@GetMapping("/myCollection/item")
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

        return "admin/viewUserCollectionItems";
    }
*/

    @GetMapping("/myCollection/item")
    public String itemList(@RequestParam("id") Long id, Model model, Principal principal) {
        Optional<UserCollection> collectionOptional = userCollectionService.getCollectionById(id);
        User user = userRepository.findByEmail(principal.getName());

        if (collectionOptional.isPresent()) {
            UserCollection collection = collectionOptional.get();
            List<UserCollectionItems> items = userCollectionItemsService.getItemsByCollectionAndUser(collection, user);

            model.addAttribute("collectionItem", collection);
            model.addAttribute("items", items);
        } else {
            return "redirect:/error";
        }

        return "admin/viewUserCollectionItems";
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

            return "admin/viewUserCollectionItems"; // Render the view_item page
        } else {
            return "redirect:/error"; // Redirect to an error page if the item is not found
        }
    }


















    // Invalidates all active sessions for a specific user, effectively logging them out.
    public void invalidateUserSessions(String email) {
        List<Object> principals = sessionRegistry.getAllPrincipals(); // Get all principals (logged-in users).
        for (Object principal : principals) {
            if (principal instanceof UserDetails userDetails) {
                if (userDetails.getUsername().equals(email)) {
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
                    for (SessionInformation sessionInfo : sessions) {
                        sessionInfo.expireNow(); // Invalidate the session.
                    }
                }
            }
        }
    }

    // Handles the request to block one or more users.
    @PostMapping("/block")
    @ResponseBody
    public ResponseEntity<String> blockUser(@RequestBody List<Long> userIds, HttpServletRequest request) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId); // Retrieve the user by ID.
            if (user != null) {
                user.setActive(false); // Set the user's status to inactive (blocked).
                userDataService.saveUser(user); // Save the user's status.
                invalidateUserSessions(user.getEmail()); // Invalidate the user's active sessions.

                // Invalidate the session if the blocked user is logged in.
                if (request.getUserPrincipal() != null && request.getUserPrincipal().getName().equals(user.getEmail())) {
                    request.getSession().invalidate();
                }
            }
        }
        return ResponseEntity.ok("Users blocked successfully"); // Return a success response.
    }

    // Handles the request to unblock one or more users.
    @PostMapping("/unblock")
    @ResponseBody
    public ResponseEntity<String> unblockUser(@RequestBody List<Long> userIds) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId); // Retrieve the user by ID.
            if (user != null) {
                user.setActive(true); // Set the user's status to active (unblocked).
                userDataService.saveUser(user); // Save the user's status.
            }
        }
        return ResponseEntity.ok("Users unblocked successfully"); // Return a success response.
    }

    // Handles the request to delete one or more users.
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@RequestBody List<Long> userIds, HttpServletRequest request) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId); // Retrieve the user by ID.
            if (user != null) {
                userDataService.deleteUser(userId); // Delete the user.
                invalidateUserSessions(user.getEmail()); // Invalidate the user's active sessions.

                // Invalidate the session if the deleted user is logged in.
                if (request.getUserPrincipal() != null && request.getUserPrincipal().getName().equals(user.getEmail())) {
                    request.getSession().invalidate();
                }
            }
        }
        return ResponseEntity.ok("Users deleted successfully"); // Return a success response.
    }

    // Handles the request to promote one or more users to admin status.
    @PostMapping("/make_admin")
    @ResponseBody
    public ResponseEntity<String> makeAdmin(@RequestBody List<Long> userIds) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId); // Retrieve the user by ID.
            if (user != null) {
                user.setRole("ADMIN"); // Set the user's role to ADMIN.
                userDataService.saveUser(user); // Save the user's role.
            }
        }
        return ResponseEntity.ok("Added to admin successfully"); // Return a success response.
    }

    // Handles the request to demote one or more admins to regular user status.
    @PostMapping("/remove_admin")
    @ResponseBody
    public ResponseEntity<String> removeAdmin(@RequestBody List<Long> userIds, HttpServletRequest request) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId); // Retrieve the user by ID.
            if (user != null) {
                user.setRole("USER"); // Set the user's role to USER.
                userDataService.saveUser(user); // Save the user's role.
                invalidateUserSessions(user.getEmail()); // Invalidate the user's active sessions.

                // Invalidate the session if the removed admin is logged in.
                if (request.getUserPrincipal() != null && request.getUserPrincipal().getName().equals(user.getEmail())) {
                    request.getSession().invalidate();
                }
            }
        }
        return ResponseEntity.ok("Removed from admin"); // Return a success response.
    }

}
