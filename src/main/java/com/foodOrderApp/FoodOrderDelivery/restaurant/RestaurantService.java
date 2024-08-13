package com.foodOrderApp.FoodOrderDelivery.restaurant;

import com.foodOrderApp.FoodOrderDelivery.user.User;
import com.foodOrderApp.FoodOrderDelivery.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    private UserService userService;

    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public boolean createRestaurant(Restaurant restaurant) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        restaurant.setOwner(user);
        Restaurant created = restaurantRepository.save(restaurant);
        if(created.getId() != null) {
            return true;
        }
        return false;
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public boolean updateRestaurantById(Long id, Restaurant restaurant) {
        Restaurant oldRestaurant = this.getRestaurantById(id);
        if(oldRestaurant != null) {
            restaurant.setId(oldRestaurant.getId());
            restaurant.setOwner(oldRestaurant.getOwner());
            restaurantRepository.save(restaurant);
            return true;
        }
        return false;
    }

    public boolean deleteRestaurantById(Long id) {
        Restaurant restaurant = this.getRestaurantById(id);
        if(restaurant != null) {
            restaurantRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
