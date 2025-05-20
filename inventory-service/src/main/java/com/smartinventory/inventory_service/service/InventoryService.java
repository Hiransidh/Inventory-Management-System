package com.smartinventory.inventory_service.service;

import com.smartinventory.inventory_service.dto.InventoryRequest;
import com.smartinventory.inventory_service.dto.InventoryResponse;
import com.smartinventory.inventory_service.entity.Inventory;
import com.smartinventory.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory addInventoryItem(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .name(inventoryRequest.getName())
                .quantity(inventoryRequest.getQuantity())
                .location(inventoryRequest.getLocation())
                .category(inventoryRequest.getCategory())
                .price(inventoryRequest.getPrice())
                .supplier(inventoryRequest.getSupplier())
                .dateAdded(inventoryRequest.getDateAdded())
                .description(inventoryRequest.getDescription())
                .build();
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventoryItem(Long id, InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        inventory.setName(inventoryRequest.getName());
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventory.setLocation(inventoryRequest.getLocation());
        inventory.setCategory(inventoryRequest.getCategory());
        inventory.setPrice(inventoryRequest.getPrice());
        inventory.setSupplier(inventoryRequest.getSupplier());
        inventory.setDateAdded(inventoryRequest.getDateAdded());
        inventory.setDescription(inventoryRequest.getDescription());
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryItemById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public void deleteInventoryItem(Long id) {
        inventoryRepository.deleteById(id);
    }

    public boolean isProductAvailable(String name, int quantity) {
        Inventory inventory = inventoryRepository.findByName(name)
                .orElse(null);
        return inventory != null && inventory.getQuantity() >= quantity;
    }

    public void reduceStock(String productName, int quantityOrdered) {
        Inventory inventory = inventoryRepository.findByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

        int currentStock = inventory.getQuantity();
        if (currentStock < quantityOrdered) {
            throw new RuntimeException("Insufficient stock for product: " + productName);
        }

        inventory.setQuantity(currentStock - quantityOrdered);
        inventoryRepository.save(inventory);
    }

}
