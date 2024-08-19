package com.foodOrderApp.FoodOrderDelivery.cart;

import com.foodOrderApp.FoodOrderDelivery.cartItem.CartItemResponseDTO;

import java.util.List;

public class CartResponseDTO {
    private Long id;
    private Long totalPrice;
    private List<CartItemResponseDTO> cartItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemResponseDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponseDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
