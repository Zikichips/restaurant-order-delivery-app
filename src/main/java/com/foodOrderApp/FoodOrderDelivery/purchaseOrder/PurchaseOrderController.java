package com.foodOrderApp.FoodOrderDelivery.purchaseOrder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subscriber")
public class PurchaseOrderController {

    private PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<PurchaseOrderResponseDTO>> getAllOrders() {
            List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllUserOrders();
            List<PurchaseOrderResponseDTO> orderResponseDTO = purchaseOrders.stream()
                .map(purchaseOrder -> {
                    PurchaseOrderResponseDTO responseDTO = new PurchaseOrderResponseDTO();
                    responseDTO.setId(purchaseOrder.getId());
                    responseDTO.setOrderDate(purchaseOrder.getOrderDate());
                    responseDTO.setPaymentStatus(purchaseOrder.getPaymentStatus().toString());
                    responseDTO.setShippingAddress(purchaseOrder.getShippingAddress());
                    responseDTO.setTotalAmount(purchaseOrder.getTotalAmount());

                    return responseDTO;
                }).toList();
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<PurchaseOrderResponseDTO> getOrderById(@PathVariable Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getOrderById(id);
        if(purchaseOrder.getId() != null) {
            PurchaseOrderResponseDTO responseDTO = new PurchaseOrderResponseDTO();
            responseDTO.setId(purchaseOrder.getId());
            responseDTO.setOrderDate(purchaseOrder.getOrderDate());
            responseDTO.setPaymentStatus(purchaseOrder.getPaymentStatus().toString());
            responseDTO.setShippingAddress(purchaseOrder.getShippingAddress());
            responseDTO.setTotalAmount(purchaseOrder.getTotalAmount());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
