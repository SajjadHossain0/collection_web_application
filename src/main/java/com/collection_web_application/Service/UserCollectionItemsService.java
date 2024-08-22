package com.collection_web_application.Service;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Entities.UserCollectionItems;

import java.util.List;
import java.util.Optional;

public interface UserCollectionItemsService {

    List<UserCollectionItems> getAllItemsWithCollections();
    List<UserCollectionItems> getItemsByCollection(UserCollection collection);

    List<UserCollectionItems> getItemsByCollectionAndUser(UserCollection userCollection, User user);

    List<UserCollectionItems> getAllItems();

    Optional<UserCollectionItems> getItemById(Long id);


}
