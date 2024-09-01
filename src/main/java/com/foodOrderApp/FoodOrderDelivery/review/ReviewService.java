package com.foodOrderApp.FoodOrderDelivery.review;

import com.foodOrderApp.FoodOrderDelivery.orderItem.OrderItem;
import com.foodOrderApp.FoodOrderDelivery.purchaseOrder.PurchaseOrder;
import com.foodOrderApp.FoodOrderDelivery.user.User;
import com.foodOrderApp.FoodOrderDelivery.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private UserService userService;
    private ReviewRepository reviewRepository;

    public ReviewService(UserService userService, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.reviewRepository = reviewRepository;
    }

    public boolean postReview(ReviewDTO review) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());

        Review newReview = new Review();


        // check if the authenticated user actually ordered from the restaurant
        if(review.getReviewEntityType().equals("RESTAURANT")) {
            System.out.println("It's a restaurant");
            PurchaseOrder purchaseOrder = authenticatedUser.getOrders().stream()
                    .filter(order -> order.getRestaurant().getId() == review.getReviewEntityId())
                    .findFirst().orElse(null);
            if(purchaseOrder.getId() != null) {
                System.out.println("Found purchase order");
                newReview.setReviewEntityType(review.getReviewEntityType());
                newReview.setReviewEntityId(review.getReviewEntityId());
                newReview.setComment(review.getComment());
                newReview.setRating(review.getRating());
                newReview.setUser(authenticatedUser);
                reviewRepository.save(newReview);
                return true;

            }
        }

        // check if the authenticated user actually ordered the particular menu item
        else if(review.getReviewEntityType().equals("MENUITEM")) {
            Optional<OrderItem> purchaseOrderItem = authenticatedUser.getOrders().stream()
                    .flatMap(order -> order.getOrderItems().stream())
                    .filter(orderItem -> orderItem.getMenuItem().getId() == review.getReviewEntityId())
                    .findFirst();

            if(purchaseOrderItem.isPresent()) {
                newReview.setReviewEntityType(review.getReviewEntityType());
                newReview.setReviewEntityId(review.getReviewEntityId());
                newReview.setComment(review.getComment());
                newReview.setRating(review.getRating());
                newReview.setComment(review.getComment());
                newReview.setRating(review.getRating());
                newReview.setUser(authenticatedUser);
                reviewRepository.save(newReview);
                return true;
            }
        }

            return false;

    }

    public List<Review> findReviewsByMenuItem(Long menuItemId) {
        return reviewRepository.findByReviewEntityIdAndReviewEntityType(menuItemId, "MENUITEM");
    }

    public List<Review> findReviewsByRestaurant(Long restaurantId) {
        return reviewRepository.findByReviewEntityIdAndReviewEntityType(restaurantId, "RESTAURANT");
    }
}
