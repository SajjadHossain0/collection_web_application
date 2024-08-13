package com.collection_web_application.Entities;


import jakarta.persistence.*;

import java.sql.Blob;


@Entity
@Table(name = "user_collection")
public class UserCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "cover_photo")
    private Blob coverPhoto;

    private String title;
    private String description;
    private String collection_creation_date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public UserCollection() {
    }

    public UserCollection(Long id, Blob coverPhoto, String title, String description, String collection_creation_date, User user) {
        this.id = id;
        this.coverPhoto = coverPhoto;
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

    public Blob getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(Blob coverPhoto) {
        this.coverPhoto = coverPhoto;
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
