package com.collection_web_application.Entities;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

//@Entity
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String full_name;
    @Column(unique = true)
    private String email;

    private String password;

}
