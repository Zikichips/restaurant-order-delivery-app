package com.foodOrderApp.FoodOrderDelivery.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriber")
public class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        List<RestaurantResponseDTO> listOfRestaurantsDTO = restaurants.stream()
                .map(restaurant -> {
                    RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO();
                    restaurantResponseDTO.setId(restaurant.getId());
                    restaurantResponseDTO.setName(restaurant.getName());
                    restaurantResponseDTO.setAddress(restaurant.getAddress());
                    restaurantResponseDTO.setPhoneNumber(restaurant.getPhoneNumber());
                    restaurantResponseDTO.setOwner_id(restaurant.getOwner().getId());
                    return  restaurantResponseDTO;
                }).toList();

        return new ResponseEntity<List<RestaurantResponseDTO>>(listOfRestaurantsDTO, HttpStatus.OK);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if(restaurant.getId() != null) {
            RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO();
            restaurantResponseDTO.setId(restaurant.getId());
            restaurantResponseDTO.setName(restaurant.getName());
            restaurantResponseDTO.setAddress(restaurant.getAddress());
            restaurantResponseDTO.setPhoneNumber(restaurant.getPhoneNumber());
            restaurantResponseDTO.setOwner_id(restaurant.getOwner().getId());
            return new ResponseEntity<>(restaurantResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PostMapping("restaurants")
    public ResponseEntity<String> createRestaurant(@RequestBody Restaurant restaurant) {
        boolean created = restaurantService.createRestaurant(restaurant);
        if(created) {
            return new ResponseEntity<>("Restaurant created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Restaurant could not be created", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PutMapping("/restaurants/{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        boolean updated = restaurantService.updateRestaurantById(id, restaurant);
        if(updated) {
            return new ResponseEntity<>("Restaurant updated successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Restaurant could not be updated", HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasAnyRole('RESTAURANT', 'ADMIN')")
    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        boolean deleted = restaurantService.deleteRestaurantById(id);

        if(deleted) {
            return new ResponseEntity<>("Restaurant deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Restaurant could not be deleted", HttpStatus.BAD_REQUEST);
    }
}
