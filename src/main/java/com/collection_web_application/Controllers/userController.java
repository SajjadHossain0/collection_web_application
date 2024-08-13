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

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws SQLException {
        Optional<UserCollection> collection = userCollectionRepository.findById(id);

        if (collection.isPresent() && collection.get().getCoverPhoto() != null) {
            Blob coverPhotoBlob = collection.get().getCoverPhoto();
            byte[] imageBytes = coverPhotoBlob.getBytes(1, (int) coverPhotoBlob.length());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);  // You can adjust this if your image is PNG
            headers.setContentLength(imageBytes.length);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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