package com.collection_web_application.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;

@Entity
@Table(name = "user_collection")
@Setter
@Getter
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


    @Transient
    private int numberOfItems;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "userCollection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserCollectionItems> items;  // Make sure this is a collection type




    private Boolean custom_string1_state;
    private String custom_string1_name;
    private Boolean custom_int1_state;
    private String custom_int1_name;

    private Boolean custom_string2_state;
    private String custom_string2_name;
    private Boolean custom_int2_state;
    private String custom_int2_name;

    private Boolean custom_string3_state;
    private String custom_string3_name;
    private Boolean custom_int3_state;
    private String custom_int3_name;



    public UserCollection() {
    }

    public UserCollection(Long id, String title, String description, String collection_creation_date, User user, List<UserCollectionItems> items, Boolean custom_string1_state, String custom_string1_name, Boolean custom_int1_state, String custom_int1_name, Boolean custom_string2_state, String custom_string2_name, Boolean custom_int2_state, String custom_int2_name, Boolean custom_string3_state, String custom_string3_name, Boolean custom_int3_state, String custom_int3_name) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.collection_creation_date = collection_creation_date;
        this.user = user;
        this.items = items;
        this.custom_string1_state = custom_string1_state;
        this.custom_string1_name = custom_string1_name;
        this.custom_int1_state = custom_int1_state;
        this.custom_int1_name = custom_int1_name;
        this.custom_string2_state = custom_string2_state;
        this.custom_string2_name = custom_string2_name;
        this.custom_int2_state = custom_int2_state;
        this.custom_int2_name = custom_int2_name;
        this.custom_string3_state = custom_string3_state;
        this.custom_string3_name = custom_string3_name;
        this.custom_int3_state = custom_int3_state;
        this.custom_int3_name = custom_int3_name;
    }

    @PostLoad
    public void calculateNumberOfItems() {
        if (items != null) {
            this.numberOfItems = items.size();
        } else {
            this.numberOfItems = 0;
        }
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

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserCollectionItems> getItems() {
        return items;
    }

    public void setItems(List<UserCollectionItems> items) {
        this.items = items;
    }

    public Boolean getCustom_string1_state() {
        return custom_string1_state;
    }

    public void setCustom_string1_state(Boolean custom_string1_state) {
        this.custom_string1_state = custom_string1_state;
    }

    public String getCustom_string1_name() {
        return custom_string1_name;
    }

    public void setCustom_string1_name(String custom_string1_name) {
        this.custom_string1_name = custom_string1_name;
    }

    public Boolean getCustom_int1_state() {
        return custom_int1_state;
    }

    public void setCustom_int1_state(Boolean custom_int1_state) {
        this.custom_int1_state = custom_int1_state;
    }

    public String getCustom_int1_name() {
        return custom_int1_name;
    }

    public void setCustom_int1_name(String custom_int1_name) {
        this.custom_int1_name = custom_int1_name;
    }

    public Boolean getCustom_string2_state() {
        return custom_string2_state;
    }

    public void setCustom_string2_state(Boolean custom_string2_state) {
        this.custom_string2_state = custom_string2_state;
    }

    public String getCustom_string2_name() {
        return custom_string2_name;
    }

    public void setCustom_string2_name(String custom_string2_name) {
        this.custom_string2_name = custom_string2_name;
    }

    public Boolean getCustom_int2_state() {
        return custom_int2_state;
    }

    public void setCustom_int2_state(Boolean custom_int2_state) {
        this.custom_int2_state = custom_int2_state;
    }

    public String getCustom_int2_name() {
        return custom_int2_name;
    }

    public void setCustom_int2_name(String custom_int2_name) {
        this.custom_int2_name = custom_int2_name;
    }

    public Boolean getCustom_string3_state() {
        return custom_string3_state;
    }

    public void setCustom_string3_state(Boolean custom_string3_state) {
        this.custom_string3_state = custom_string3_state;
    }

    public String getCustom_string3_name() {
        return custom_string3_name;
    }

    public void setCustom_string3_name(String custom_string3_name) {
        this.custom_string3_name = custom_string3_name;
    }

    public Boolean getCustom_int3_state() {
        return custom_int3_state;
    }

    public void setCustom_int3_state(Boolean custom_int3_state) {
        this.custom_int3_state = custom_int3_state;
    }

    public String getCustom_int3_name() {
        return custom_int3_name;
    }

    public void setCustom_int3_name(String custom_int3_name) {
        this.custom_int3_name = custom_int3_name;
    }
}