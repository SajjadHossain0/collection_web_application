package com.collection_web_application.Service;


import com.collection_web_application.Entities.User;

import java.util.List;

public interface UserDataService {

    List<User> getAllUsers();

    User getUserByID(Long userId);

    void saveUser(User user);

    void deleteUser(Long userId);

}