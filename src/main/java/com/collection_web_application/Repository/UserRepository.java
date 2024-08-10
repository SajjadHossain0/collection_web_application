package com.collection_web_application.Repository;

import com.collection_web_application.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    @Query("SELECT x FROM User x WHERE x.email = :email OR x.role LIKE %:role%")
    List<User> findByEmailOrRole(@Param("email") String email, @Param("role") String role);

}