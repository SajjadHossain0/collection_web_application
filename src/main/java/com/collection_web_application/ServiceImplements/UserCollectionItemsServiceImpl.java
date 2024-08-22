package com.collection_web_application.ServiceImplements;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Entities.UserCollectionItems;
import com.collection_web_application.Repository.UserCollectionItemsRepository;
import com.collection_web_application.Service.UserCollectionItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCollectionItemsServiceImpl implements UserCollectionItemsService {

    @Autowired
    UserCollectionItemsRepository userCollectionItemsRepository;

    @Override
    public List<UserCollectionItems> getAllItemsWithCollections() {
        return userCollectionItemsRepository.findAll();
    }

    @Override
    public List<UserCollectionItems> getItemsByCollection(UserCollection collection) {
        return userCollectionItemsRepository.findAll();
    }

    @Override
    public List<UserCollectionItems> getItemsByCollectionAndUser(UserCollection userCollection, User user) {
        return userCollectionItemsRepository.findByUserCollectionAndUser(userCollection, user);
    }

    @Override
    public List<UserCollectionItems> getAllItems() {
        return userCollectionItemsRepository.findAll();
    }

    @Override
    public Optional<UserCollectionItems> getItemById(Long id) {
        return userCollectionItemsRepository.findById(id);
    }
}
