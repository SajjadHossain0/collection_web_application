package com.collection_web_application.Service;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;

import java.util.List;

public interface UserCollectionService {

    List<UserCollection> getAllCollections();
    List<UserCollection> getCollectionsByUser(User user);


}
