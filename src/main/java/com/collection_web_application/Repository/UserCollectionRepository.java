package com.collection_web_application.Repository;

import com.collection_web_application.Entities.User;
import com.collection_web_application.Entities.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {

    List<UserCollection> findByUser(User User);
    Optional<UserCollection> findByIdAndUser(Long id, User user);


}
