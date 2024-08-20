package com.collection_web_application.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "user_table", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required.")
    private String name;
    @Email(message = "Please provide a valid email address.")
    @NotEmpty(message = "Email is required.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;

    private String role;
    private boolean active = true;
    private String lastLoginTime;
    private String registrationTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<UserCollection> collections;

    public User() {
    }

    public User(Long id, String name, String email, String password, String role, boolean active, String lastLoginTime, String registrationTime, Set<UserCollection> collections) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
        this.lastLoginTime = lastLoginTime;
        this.registrationTime = registrationTime;
        this.collections = collections;
    }

}
