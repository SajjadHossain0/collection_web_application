package com.collection_web_application.Controllers;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Repository.UserCollectionRepository;
import com.collection_web_application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

@Controller
@RequestMapping("/user")
public class userController {
    //Authenticated users page

    @Autowired
    private UserCollectionRepository userCollectionRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String user() {

        return "user/user_page";
    }

    @PostMapping("/add_collection")
    public String addCollection(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("coverPhoto") MultipartFile coverPhoto,
            Principal principal, // This provides the current user's principal
            Model model) throws IOException, SQLException {

        UserCollection userCollection = new UserCollection();
        userCollection.setTitle(title);
        userCollection.setDescription(description);

        // Convert cover photo to Base64 and store it as a string
        //userCollection.setCoverPhoto(Base64.getEncoder().encodeToString(coverPhoto.getBytes()));

        //userCollection.setCoverPhoto(coverPhoto.getOriginalFilename());

// Convert cover photo to Blob
        if (!coverPhoto.isEmpty()) {
            InputStream inputStream = coverPhoto.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            Blob blob = new SerialBlob(bytes);
            userCollection.setCoverPhoto(blob);
        }

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


}