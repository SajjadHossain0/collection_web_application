package com.collection_web_application.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


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

    public UserCollection(Long id, String title, String description, String collection_creation_date, User user, Boolean custom_string1_state, String custom_string1_name, Boolean custom_int1_state, String custom_int1_name, Boolean custom_string2_state, String custom_string2_name, Boolean custom_int2_state, String custom_int2_name, Boolean custom_string3_state, String custom_string3_name, Boolean custom_int3_state, String custom_int3_name) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.collection_creation_date = collection_creation_date;
        this.user = user;
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

}
