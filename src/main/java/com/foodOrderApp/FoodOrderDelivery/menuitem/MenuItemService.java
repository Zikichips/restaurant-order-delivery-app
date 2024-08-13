package com.foodOrderApp.FoodOrderDelivery.menuitem;

import com.foodOrderApp.FoodOrderDelivery.restaurant.Restaurant;
import com.foodOrderApp.FoodOrderDelivery.restaurant.RestaurantService;
import com.foodOrderApp.FoodOrderDelivery.user.User;
import com.foodOrderApp.FoodOrderDelivery.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    private MenuItemRepository menuItemRepository;
    private UserService userService;
    private RestaurantService restaurantService;

    public MenuItemService(MenuItemRepository menuItemRepository, UserService userService, RestaurantService restaurantService) {
        this.menuItemRepository = menuItemRepository;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> findAllByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public boolean createMenuItem(CreateMenuItemDTO menuItem) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());

        Restaurant restaurant = restaurantService.getRestaurantById(menuItem.getRestaurant_id());
        if(restaurant.getId() != null) {
            if(authenticatedUser.getId() == restaurant.getOwner().getId()) {
                MenuItem newMenuItem = new MenuItem();
                newMenuItem.setName(menuItem.getName());
                newMenuItem.setDescription(menuItem.getDescription());
                newMenuItem.setPrice(menuItem.getPrice());
                newMenuItem.setRestaurant(restaurant);
                menuItemRepository.save(newMenuItem);
                return true;
            }

        }
        return false;
    }

    public boolean updateMenuItemById(Long id, CreateMenuItemDTO menuItemDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());

        Restaurant restaurant = restaurantService.getRestaurantById(menuItemDTO.getRestaurant_id());
        if(restaurant.getId() != null) {
            if(authenticatedUser.getId() == restaurant.getOwner().getId()) {
                MenuItem menuItemToUpdate = restaurant.getMenuItems().stream()
                        .filter(menuItem -> menuItem.getId().equals(id))
                        .findFirst().orElse(null);

                MenuItem newMenuItem = new MenuItem();
                newMenuItem.setId(menuItemToUpdate.getId());
                newMenuItem.setName(menuItemDTO.getName());
                newMenuItem.setDescription(menuItemDTO.getDescription());
                newMenuItem.setPrice(menuItemDTO.getPrice());
                newMenuItem.setRestaurant(restaurant);
                menuItemRepository.save(newMenuItem);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMenuItemById(Long id) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());

        Restaurant restaurant = menuItemRepository.findById(id).get().getRestaurant();
        if (restaurant.getId() != null) {
            if (authenticatedUser.getId() == restaurant.getOwner().getId()) {
               menuItemRepository.deleteById(id);
               return true;
            }
        }
        return false;
    }
}
