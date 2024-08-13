package com.foodOrderApp.FoodOrderDelivery.cart;

import com.foodOrderApp.FoodOrderDelivery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
