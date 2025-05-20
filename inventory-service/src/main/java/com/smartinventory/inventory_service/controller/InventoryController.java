package com.smartinventory.inventory_service.controller;

import com.smartinventory.inventory_service.dto.InventoryRequest;
import com.smartinventory.inventory_service.entity.Inventory;
import com.smartinventory.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER')")
    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryService.addInventoryItem(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventory);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryService.updateInventoryItem(id, inventoryRequest);
        return ResponseEntity.ok(inventory);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventoryList = inventoryService.getAllInventoryItems();
        return ResponseEntity.ok(inventoryList);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INVENTORY_MANAGER', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryItemById(id);
        return ResponseEntity.ok(inventory);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/check")
    public ResponseEntity<Boolean> isProductAvailable(@RequestParam String name, @RequestParam int quantity) {
        boolean available = inventoryService.isProductAvailable(name, quantity);
        return ResponseEntity.ok(available);
    }
}
