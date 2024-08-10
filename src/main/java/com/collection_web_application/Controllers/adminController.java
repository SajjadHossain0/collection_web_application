package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
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

import java.util.List;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private UserDataService userDataService;
    @Autowired
    private SessionRegistry sessionRegistry;

    // Admin page
    @GetMapping("")
    public String admin(Model model){

        model.addAttribute("userDetails", userDataService.getAllUsers());

        return "admin/admin_page";
    }

    // search

    @GetMapping("/userDetails/search")
    public String searchUserByEmailOrRole(@RequestParam("query") String query, Model model) {
        List<User> searchResults = userDataService.searchUserByEmailOrRole(query);
        model.addAttribute("userDetails", searchResults);
        return "admin/admin_page";
    }


    // user action buttons

    public void invalidateUserSessions(String username) {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
                    for (SessionInformation sessionInfo : sessions) {
                        sessionInfo.expireNow(); // Invalidate the session
                    }
                }
            }
        }
    }

    @PostMapping("/block")
    @ResponseBody
    public ResponseEntity<String> blockUser(@RequestBody List<Long> userIds, HttpServletRequest request) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId);
            if (user != null) {
                user.setActive(false);
                userDataService.saveUser(user);
                invalidateUserSessions(user.getEmail()); // Invalidate sessions

                // Invalidate session if the blocked user is logged in
                if (request.getUserPrincipal() != null && request.getUserPrincipal().getName().equals(user.getEmail())) {
                    request.getSession().invalidate();
                }
            }
        }
        return ResponseEntity.ok("Users blocked successfully");
    }

    @PostMapping("/unblock")
    @ResponseBody
    public ResponseEntity<String> unblockUser(@RequestBody List<Long> userIds) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId);
            if (user != null) {
                user.setActive(true);
                userDataService.saveUser(user);
            }
        }
        return ResponseEntity.ok("Users unblocked successfully");
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@RequestBody List<Long> userIds, HttpServletRequest request) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId);
            if (user != null) {
                userDataService.deleteUser(userId);
                invalidateUserSessions(user.getEmail()); // Invalidate sessions

                // Invalidate session if the deleted user is logged in
                if (request.getUserPrincipal() != null && request.getUserPrincipal().getName().equals(user.getEmail())) {
                    request.getSession().invalidate();
                }
            }
        }
        return ResponseEntity.ok("Users deleted successfully");
    }

    @PostMapping("/make_admin")
    @ResponseBody
    public ResponseEntity<String> makeAdmin(@RequestBody List<Long> userIds) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId);
            if (user != null) {
                user.setRole("ADMIN");
                userDataService.saveUser(user);
            }
        }
        return ResponseEntity.ok("Added to admin successfully");
    }

    @PostMapping("/remove_admin")
    @ResponseBody
    public ResponseEntity<String> removeAdmin(@RequestBody List<Long> userIds, HttpServletRequest request) {
        for (Long userId : userIds) {
            User user = userDataService.getUserByID(userId);
            if (user != null) {
                user.setRole("USER");
                userDataService.saveUser(user);
                invalidateUserSessions(user.getEmail()); // Invalidate sessions

                // Invalidate session if the removed admin is logged in
                if (request.getUserPrincipal() != null && request.getUserPrincipal().getName().equals(user.getEmail())) {
                    request.getSession().invalidate();
                }
            }
        }
        return ResponseEntity.ok("Removed from admin");
    }
}