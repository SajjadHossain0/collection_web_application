package com.collection_web_application.Repository;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import com.collection_web_application.Entities.UserCollectionItems;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCollectionItemsRepository extends JpaRepository<UserCollectionItems, Long> {

    List<UserCollectionItems> findByUserCollectionAndUser(UserCollection userCollection, User user);
}
