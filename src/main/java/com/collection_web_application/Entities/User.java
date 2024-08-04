package com.collection_web_application.Entities;

import jakarta.persistence.*;

//@Entity
@Table(name = "users_table")
public class User {
    @Id
    private Long id;

    private String full_name;

    private String email;

    private String password;

}
