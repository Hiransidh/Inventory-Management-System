package com.smartinventory.order_service.service;

import com.smartinventory.order_service.dto.OrderRequest;
import com.smartinventory.order_service.entity.Order;
import com.smartinventory.order_service.entity.OrderStatus;
import com.smartinventory.order_service.kafka.OrderKafkaProducer;
import com.smartinventory.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final OrderKafkaProducer orderKafkaProducer;  // Inject Kafka Producer

    public Order placeOrder(OrderRequest request, String customerEmail) {

        String token = null;
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuthToken) {
            token = jwtAuthToken.getToken().getTokenValue();
        }

        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.setBearerAuth(token);
        }

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String inventoryUrl = "http://localhost:8083/api/inventory/check"
                + "?name=" + request.getProductName()
                + "&quantity=" + request.getQuantityOrdered();

        ResponseEntity<Boolean> response = restTemplate.exchange(
                inventoryUrl,
                HttpMethod.GET,
                entity,
                Boolean.class
        );

        Boolean available = response.getBody();

        if (available == null || !available) {
            throw new RuntimeException("Product not available or insufficient stock");
        }

        Order order = Order.builder()
                .productName(request.getProductName())
                .quantityOrdered(request.getQuantityOrdered())
                .price(request.getPrice())
                .deliveryAddress(request.getDeliveryAddress())
                .status(OrderStatus.PLACED)
                .customerEmail(customerEmail)
                .build();

        Order savedOrder = orderRepository.save(order);

        // Send Kafka event after saving the order
        orderKafkaProducer.sendOrderEvent(savedOrder);

        return savedOrder;
    }

    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}
