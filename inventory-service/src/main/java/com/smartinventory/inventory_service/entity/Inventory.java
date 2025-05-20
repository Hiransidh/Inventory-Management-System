package com.smartinventory.inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int quantity;

    private String location;

    private String category;

    private double price;

    private String supplier;

    private LocalDate dateAdded;

    @Column(length = 1000)
    private String description;


}
