package com.smartinventory.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private int quantityOrdered;

    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // e.g., "PLACED", "SHIPPED"

    private String deliveryAddress;

    private String customerEmail; // From JWT
}
