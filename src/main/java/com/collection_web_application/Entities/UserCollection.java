package com.collection_web_application.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Blob;


@Entity
@Table(name = "user_collection")
public class UserCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(length = 20)
    private String title;

    @NotNull
    @Size(max = 150)
    @Column(length = 150)
    private String description;
    private String collection_creation_date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public UserCollection() {
    }

    public UserCollection(Long id, String title, String description, String collection_creation_date, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.collection_creation_date = collection_creation_date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCollection_creation_date() {
        return collection_creation_date;
    }

    public void setCollection_creation_date(String collection_creation_date) {
        this.collection_creation_date = collection_creation_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
