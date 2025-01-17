package com.collection_web_application.Service;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;

import java.util.List;
import java.util.Optional;

public interface UserCollectionService {

    List<UserCollection> getAllCollections();

    List<UserCollection> getCollectionsByUser(User user);

    Optional<UserCollection> getCollectionById(Long id);

    public String importCollections(String apiToken);
}
