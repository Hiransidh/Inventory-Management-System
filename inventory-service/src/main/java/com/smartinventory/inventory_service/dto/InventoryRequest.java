package com.smartinventory.inventory_service.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {
    private String name;
    private int quantity;
    private String location;
    private String category;
    private double price;
    private String supplier;
    private LocalDate dateAdded;
    private String description;
}
