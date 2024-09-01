package com.foodOrderApp.FoodOrderDelivery.review;

public class ReviewResponseDTO {

    private Long id;
    private Long user_id;
    private String comment;
    private Long rating;
    private Long reviewEntityId;
    private String reviewEntityType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
