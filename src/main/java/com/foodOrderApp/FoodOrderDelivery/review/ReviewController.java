package com.foodOrderApp.FoodOrderDelivery.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriber")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> postReview(@RequestBody ReviewDTO review) {
        boolean created = reviewService.postReview(review);
        if(created) {
            return new ResponseEntity<>("Review created  successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Review could not be created", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/menu-items/{menu_item_id}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsForMenuItem(@PathVariable Long menu_item_id) {
        List<Review> reviews = reviewService.findReviewsByMenuItem(menu_item_id);
        List<ReviewResponseDTO> reviewResponseDTOS = reviews.stream()
                .map(review -> {
                    ReviewResponseDTO responseDTO = new ReviewResponseDTO();
                    responseDTO.setId(review.getId());
                    responseDTO.setComment(review.getComment());
                    responseDTO.setReviewEntityId(review.getReviewEntityId());
                    responseDTO.setRating(review.getRating());
                    responseDTO.setUser_id(review.getUser().getId());
                    responseDTO.setReviewEntityType(review.getReviewEntityType());
                    return responseDTO;
                })
                .toList();
        return new ResponseEntity<>(reviewResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restaurant_id}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsForRestaurant(@PathVariable Long restaurant_id) {
        List<Review> reviews = reviewService.findReviewsByRestaurant(restaurant_id);
        List<ReviewResponseDTO> reviewResponseDTOS = reviews.stream()
                .map(review -> {
                    ReviewResponseDTO responseDTO = new ReviewResponseDTO();
                    responseDTO.setId(review.getId());
                    responseDTO.setComment(review.getComment());
                    responseDTO.setReviewEntityId(review.getReviewEntityId());
                    responseDTO.setRating(review.getRating());
                    responseDTO.setUser_id(review.getUser().getId());
                    responseDTO.setReviewEntityType(review.getReviewEntityType());
                    return responseDTO;
                })
                .toList();
        return new ResponseEntity<>(reviewResponseDTOS, HttpStatus.OK);
    }
}
