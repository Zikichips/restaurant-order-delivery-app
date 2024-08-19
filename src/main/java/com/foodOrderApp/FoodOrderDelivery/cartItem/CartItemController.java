package com.foodOrderApp.FoodOrderDelivery.cartItem;

import com.foodOrderApp.FoodOrderDelivery.cart.CartService;
import com.foodOrderApp.FoodOrderDelivery.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriber")
public class CartItemController {
    private CartItemService cartItemService;
    private UserService userService;
    private CartService cartService;

    public CartItemController(CartItemService cartItemService, UserService userService, CartService cartService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/cartitem/{id}")
    public ResponseEntity<CartItemResponseDTO> getCartItemById(@PathVariable Long id) {
        CartItem itemExists = cartItemService.findById(id);
        if(itemExists != null) {
            CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();
            cartItemResponseDTO.setId(itemExists.getId());
            cartItemResponseDTO.setQuantity(itemExists.getQuantity());
            cartItemResponseDTO.setCart_id(itemExists.getCart().getId());
            cartItemResponseDTO.setMenuItemName(itemExists.getMenuItem().getName());
            cartItemResponseDTO.setPrice(itemExists.getMenuItem().getPrice());

            return new ResponseEntity<>(cartItemResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/cartitem")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) {
        boolean addedToCart = cartItemService.addToCart(cartDTO.getMenuitem_id(), cartDTO.getQuantity());
        if(addedToCart) {
            return new ResponseEntity<>("Item added to cart successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Item could not be added to cart", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/cartitem/{id}")
    public ResponseEntity<String> updateCartItem(@PathVariable Long id, @RequestBody CartItemQuantityDTO quantityDTO) {
        boolean updateCartItemQuantity = cartItemService.updateCartItem(id, quantityDTO.getQuantity());
        if(updateCartItemQuantity) {
            return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Item could not be updated", HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/cartitem/{id}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long id) {
        boolean deleted = cartItemService.deleteCartItemById(id);
        if(deleted) {
            return new ResponseEntity<>("Item deleted from cart", HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not delete item from cart" , HttpStatus.BAD_REQUEST);

    }

}
