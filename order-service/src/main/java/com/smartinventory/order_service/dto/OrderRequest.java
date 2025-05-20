package com.smartinventory.order_service.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Data
public class OrderRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Quantity ordered is required")
    private int quantityOrdered;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;
}
