package com.foodOrderApp.FoodOrderDelivery.orderItem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subscriber")
public class OrderItemController {
    private OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/orders/{id}/orderitems")
    public ResponseEntity<List<OrderItemResponseDTO>> getAllOrderItemsForParticularOrder(@PathVariable Long id) {
        List<OrderItem> orderItems = orderItemService.getAllOrderItemsForParticularOrder(id);
        List<OrderItemResponseDTO> orderItemsResponse = orderItems.stream()
                .map(orderItem -> {
                    OrderItemResponseDTO orderResponseDTO = new OrderItemResponseDTO();
                    orderResponseDTO.setId(orderItem.getId());
                    orderResponseDTO.setOrderItemName(orderItem.getMenuItem().getName());
                    orderResponseDTO.setOrder_id(orderItem.getOrder().getId());
                    orderResponseDTO.setQuantity(orderItem.getQuantity());
                    orderResponseDTO.setPrice(orderItem.getPrice());
                    orderResponseDTO.setMenuitem_id(orderItem.getMenuItem().getId());


                    return orderResponseDTO;
                }).toList();
        return new ResponseEntity<>(orderItemsResponse, HttpStatus.OK);
    }
}
