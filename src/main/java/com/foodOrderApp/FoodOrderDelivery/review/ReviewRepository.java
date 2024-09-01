package com.foodOrderApp.FoodOrderDelivery.review;

import com.foodOrderApp.FoodOrderDelivery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewEntityIdAndReviewEntityType(Long menuItemId, String menuitem);

    boolean existsByUserAndReviewEntityTypeAndReviewEntityId(User authenticatedUser, String reviewEntityType, Long reviewEntityId);
}
