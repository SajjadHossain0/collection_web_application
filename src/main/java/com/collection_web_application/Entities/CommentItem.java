package com.collection_web_application.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_item_table")
public class CommentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private UserCollectionItems item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CommentItem() {
    }

    public CommentItem(Long id, String comment, UserCollectionItems item, User user) {
        this.id = id;
        this.comment = comment;
        this.item = item;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserCollectionItems getItem() {
        return item;
    }

    public void setItem(UserCollectionItems item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
