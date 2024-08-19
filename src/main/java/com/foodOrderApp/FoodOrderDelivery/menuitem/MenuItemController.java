package com.foodOrderApp.FoodOrderDelivery.menuitem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriber")
public class MenuItemController {
    private MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    // Get all Menu Items
    @GetMapping("/menuitems")
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemService.findAll();
        List<MenuItemResponseDTO> allMenuItemsResponseDTO = menuItems.stream()
                .map(menuItem -> {
                    MenuItemResponseDTO menuItemResponseDTO = new MenuItemResponseDTO();
                    menuItemResponseDTO.setId(menuItem.getId());
                    menuItemResponseDTO.setName(menuItem.getName());
                    menuItemResponseDTO.setDescription(menuItem.getDescription());
                    menuItemResponseDTO.setPrice(menuItem.getPrice());
                    menuItemResponseDTO.setRestaurant_id(menuItem.getRestaurant().getId());

                    return menuItemResponseDTO;
                }).toList();
        return new ResponseEntity<>(allMenuItemsResponseDTO, HttpStatus.OK);
    }

    // Get all Menu Items associated with a Single Restaurant
    @GetMapping("/restaurant/{restaurant_id}/menuitems")
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItemsForSingleRestaurant(@PathVariable Long restaurant_id) {
        List<MenuItem> menuItems = menuItemService.findAllByRestaurantId(restaurant_id);
        List<MenuItemResponseDTO> allMenuItemsResponseDTO = menuItems.stream()
                .map(menuItem -> {
                    MenuItemResponseDTO menuItemResponseDTO = new MenuItemResponseDTO();
                    menuItemResponseDTO.setId(menuItem.getId());
                    menuItemResponseDTO.setName(menuItem.getName());
                    menuItemResponseDTO.setDescription(menuItem.getDescription());
                    menuItemResponseDTO.setPrice(menuItem.getPrice());
                    menuItemResponseDTO.setRestaurant_id(menuItem.getRestaurant().getId());

                    return menuItemResponseDTO;
                }).toList();
        return new ResponseEntity<>(allMenuItemsResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/menuitems/{id}")
    public ResponseEntity<MenuItemResponseDTO> getMenuItemById(@PathVariable Long id) {
        MenuItem itemExists = menuItemService.findById(id);
        if(itemExists != null) {
            MenuItemResponseDTO menuItemResponseDTO = new MenuItemResponseDTO();
            menuItemResponseDTO.setId(itemExists.getId());
            menuItemResponseDTO.setName(itemExists.getName());
            menuItemResponseDTO.setDescription(itemExists.getDescription());
            menuItemResponseDTO.setPrice(itemExists.getPrice());
            menuItemResponseDTO.setRestaurant_id(itemExists.getRestaurant().getId());

            return new ResponseEntity<>(menuItemResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PostMapping("/menuitems")
    public ResponseEntity<String> createMenuItem(@RequestBody CreateMenuItemDTO menuItemDTO) {
        boolean menuItemCreated = menuItemService.createMenuItem(menuItemDTO);
        if(menuItemCreated) {
            return new ResponseEntity<>("Menu item created successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(" could not create menu item", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @PutMapping("/menuitems/{id}")
    public ResponseEntity<String> updateMenuItem(@PathVariable Long id, @RequestBody CreateMenuItemDTO menuItemDTO) {
        boolean menuItemUpdated = menuItemService.updateMenuItemById(id, menuItemDTO);
        if(menuItemUpdated) {
            return new ResponseEntity<>("Menu Item updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Menu Item could not be updated", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @DeleteMapping("/menuitems/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        boolean menuItemDeleted = menuItemService.deleteMenuItemById(id);
        if(menuItemDeleted) {
            return new ResponseEntity<>("Menu Item deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Menu Item could not be deleted", HttpStatus.BAD_REQUEST);
    }


//    @DeleteMapping("/cartitem/{id}")
//    public ResponseEntity<String> removeItemFromCart(@PathVariable Long id) {
//        boolean deleted = cartItemService.deleteCartItemById(id);
//        if(deleted) {
//            return new ResponseEntity<>("Item deleted from cart", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Could not delete item from cart" , HttpStatus.BAD_REQUEST);
//
//    }
}
