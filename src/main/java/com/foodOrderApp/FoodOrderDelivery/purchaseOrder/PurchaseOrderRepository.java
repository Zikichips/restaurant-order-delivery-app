package com.foodOrderApp.FoodOrderDelivery.purchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
