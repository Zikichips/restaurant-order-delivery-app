package com.foodOrderApp.FoodOrderDelivery.cartItem;

public class CartDTO {
    private Long menuitem_id;
    private Long quantity;

    public Long getMenuitem_id() {
        return menuitem_id;
    }

    public void setMenuitem_id(Long menuitem_id) {
        this.menuitem_id = menuitem_id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
