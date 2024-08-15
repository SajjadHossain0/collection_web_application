package com.collection_web_application.ServiceImplements;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Repository.UserCollectionRepository;
import com.collection_web_application.Service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCollectionServiceImpl implements UserCollectionService {

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Override
    public List<UserCollection> getAllCollections() {
        return userCollectionRepository.findAll();
    }

    @Override
    public List<UserCollection> getCollectionsByUser(User user) {
        return userCollectionRepository.findByUser(user);
    }


}
