package com.foodOrderApp.FoodOrderDelivery.review;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.foodOrderApp.FoodOrderDelivery.user.User;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // help prevent infinite loop
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String comment;
    private Long rating;
    @Column(name = "review_entity_id")
    private Long reviewEntityId;
    @Column(name = "review_entity_type")
    private String reviewEntityType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getReviewEntityId() {
        return reviewEntityId;
    }

    public void setReviewEntityId(Long reviewEntityId) {
        this.reviewEntityId = reviewEntityId;
    }

    public String getReviewEntityType() {
        return reviewEntityType;
    }

    public void setReviewEntityType(String reviewEntityType) {
        this.reviewEntityType = reviewEntityType;
    }
}
