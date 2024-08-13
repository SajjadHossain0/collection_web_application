package com.collection_web_application.Repository;

import com.collection_web_application.Entities.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
}
