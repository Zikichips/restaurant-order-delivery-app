package com.foodOrderApp.FoodOrderDelivery.orderItem;

import com.foodOrderApp.FoodOrderDelivery.cartItem.CartItem;
import com.foodOrderApp.FoodOrderDelivery.purchaseOrder.PurchaseOrder;
import com.foodOrderApp.FoodOrderDelivery.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private OrderItemRepository orderItemRepository;
    private UserService userService;

    public OrderItemService(OrderItemRepository orderItemRepository, UserService userService) {
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
    }

    public OrderItem createOrderItem(CartItem cartItem, PurchaseOrder savedPurchaseOrder) {
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setPrice(cartItem.getMenuItem().getPrice());
        newOrderItem.setQuantity(cartItem.getQuantity());
        newOrderItem.setMenuItem(cartItem.getMenuItem());
        newOrderItem.setOrder(savedPurchaseOrder);
        OrderItem newOrderItemSaved = orderItemRepository.save(newOrderItem);
        if(newOrderItemSaved.getId() != null) {
            return newOrderItemSaved;
        }
        return null;
    }

//    public List<OrderItem> getAllUserOrders() {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userService.findByUsername(userDetails.getUsername());
//
////        List<OrderItem> userOrders = user.getOrders();
//
//
//    }
}
