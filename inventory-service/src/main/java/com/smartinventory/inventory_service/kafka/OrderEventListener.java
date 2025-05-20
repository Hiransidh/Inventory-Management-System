package com.smartinventory.inventory_service.kafka;

import com.smartinventory.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void consumeOrderCreatedEvent(OrderEvent event) {
        log.info("Consumed order event: {}", event);
        inventoryService.reduceStock(event.getProductName(), event.getQuantityOrdered());
    }
}
