package com.foodOrderApp.FoodOrderDelivery.cart;

import com.foodOrderApp.FoodOrderDelivery.cartItem.CartItemResponseDTO;
import com.foodOrderApp.FoodOrderDelivery.user.User;
import com.foodOrderApp.FoodOrderDelivery.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriber")
public class CartController {
    private CartService cartService;
    private UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponseDTO> getCartForUser(@AuthenticationPrincipal UserDetails userDetails){
        if(userDetails.getUsername() != null) {
            User user = userService.findByUsername(userDetails.getUsername());
            Cart cart = cartService.findCartByUser(user);
            if(cart != null) {
                CartResponseDTO cartResponseDTO = new CartResponseDTO();
                cartResponseDTO.setId(cart.getId());
                cartResponseDTO.setTotalPrice(cart.getTotal_price());
                cartResponseDTO.setCartItems(cart.getCartItems().stream()
                        .map(cartItem -> {
                            CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();
                            cartItemResponseDTO.setId(cartItem.getId());
                            cartItemResponseDTO.setQuantity(cartItem.getQuantity());
                            cartItemResponseDTO.setCart_id(cartItem.getCart().getId());
                            cartItemResponseDTO.setMenuItemName(cartItem.getMenuItem().getName());
                            cartItemResponseDTO.setPrice(cartItem.getMenuItem().getPrice());

                            return cartItemResponseDTO;
                        }).toList());

                return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @PutMapping("/cart/add")
}
