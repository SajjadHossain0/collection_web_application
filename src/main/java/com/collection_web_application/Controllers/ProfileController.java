package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserCollectionService;
import com.collection_web_application.Service.UserDataService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserCollectionService userCollectionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataService userDataService;

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal){
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        return "user/profile_page";
    }

    @GetMapping("/export/collections")
    public void exportCollectionsToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=collections.csv");

        // Fetch collections from the database
        List<UserCollection> collections = userCollectionService.getAllCollections();

        // Write CSV header
        PrintWriter writer = response.getWriter();
        writer.write("ID, Title, Description, Number of items\n");

        // Write CSV data
        for (UserCollection collection : collections) {
            writer.write(collection.getId() + ","
                    + collection.getTitle() + ","
                    + collection.getDescription() + ","
                    + collection.getItems().size()
                    + "\n");
        }
    }

    /*@GetMapping("/view-report")
    public String viewReport() {
        return "powerbi-report"; // This corresponds to powerbi-report.html in templates
    }*/


    @PostMapping("/profile/api-token")
    public ResponseEntity<String> getApiToken(@RequestParam Long userId) {
        String apiToken = userDataService.generateApiToken(userId);
        return ResponseEntity.ok(apiToken);
    }


}
