package com.foodOrderApp.FoodOrderDelivery.review;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewDTO {
    private String comment;
    private Long rating;
    @JsonProperty("review_entity_id")
    private Long reviewEntityId;
    @JsonProperty("review_entity_type")
    private String reviewEntityType;

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
