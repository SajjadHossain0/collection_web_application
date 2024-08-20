package com.collection_web_application.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "user_collection_item")
@Setter
@Getter
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

}
