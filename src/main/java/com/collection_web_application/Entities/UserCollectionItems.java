package com.collection_web_application.Entities;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "user_collection_item")
public class UserCollectionItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String tag;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private UserCollection userCollection;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    private Map<String, String> customStringFields = new HashMap<>();

    @ElementCollection
    private Map<String, String> customIntFields = new HashMap<>();



    public UserCollectionItems() {
    }

    public UserCollectionItems(Long id, String name, String tag, UserCollection userCollection, User user, Map<String, String> customStringFields, Map<String, String> customIntFields) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.userCollection = userCollection;
        this.user = user;
        this.customStringFields = customStringFields;
        this.customIntFields = customIntFields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public UserCollection getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(UserCollection userCollection) {
        this.userCollection = userCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, String> getCustomStringFields() {
        return customStringFields;
    }

    public void setCustomStringFields(Map<String, String> customStringFields) {
        this.customStringFields = customStringFields;
    }

    public Map<String, String> getCustomIntFields() {
        return customIntFields;
    }

    public void setCustomIntFields(Map<String, String> customIntFields) {
        this.customIntFields = customIntFields;
    }
}
