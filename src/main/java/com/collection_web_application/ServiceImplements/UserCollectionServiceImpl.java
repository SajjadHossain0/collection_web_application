package com.collection_web_application.ServiceImplements;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Repository.UserCollectionRepository;
import com.collection_web_application.Repository.UserRepository;
import com.collection_web_application.Service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserCollectionServiceImpl implements UserCollectionService {

    @Autowired
    private UserCollectionRepository userCollectionRepository;
    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate;
    //@Value("${external.api.url}")
    private String apiUrl; // URL of your external API


    public UserCollectionServiceImpl(UserCollectionRepository userCollectionRepository, UserRepository userRepository, RestTemplate restTemplate, @Value("${external.api.url}") String apiUrl) {
        this.userCollectionRepository = userCollectionRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<UserCollection> getAllCollections() {
        return userCollectionRepository.findAll();
    }

    @Override
    public List<UserCollection> getCollectionsByUser(User user) {
        return userCollectionRepository.findByUser(user);
    }

    @Override
    public Optional<UserCollection> getCollectionById(Long id) {
        return Optional.ofNullable(userCollectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Collection not found with id: " + id)));
    }

    @Override
    public String importCollections(String apiToken) {
        User user = userRepository.findByApiToken(apiToken);
        if (user == null) {
            return "Invalid API Token";
        }

        try {
            // Fetch collections from external API
            List<UserCollection> collections = fetchCollectionsFromExternalApi();
            for (UserCollection collection : collections) {
                if (collection.getUser() == null) {
                    collection.setUser(user);
                }
                userCollectionRepository.save(collection);
            }

            return "Collections imported successfully";
        } catch (Exception e) {
            return "Error importing collections: " + e.getMessage();
        }
    }
    private List<UserCollection> fetchCollectionsFromExternalApi() {
        // Replace with your actual API endpoint and response handling
        ResponseEntity<UserCollection[]> response = restTemplate.getForEntity(apiUrl, UserCollection[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }


}
