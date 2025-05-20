package com.smartinventory.order_service.kafka;

import com.smartinventory.order_service.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderKafkaProducer {
    private static final String TOPIC = "order-created";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(Order order){
        OrderEvent event = new OrderEvent(order.getId(),order.getProductName(), order.getQuantityOrdered());
        kafkaTemplate.send(TOPIC,event);
    }
}
