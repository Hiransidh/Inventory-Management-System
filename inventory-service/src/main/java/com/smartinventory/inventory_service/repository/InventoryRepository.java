package com.smartinventory.inventory_service.repository;

import com.smartinventory.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // You can add custom queries later here if needed
    // For example, find by name or category
    Optional<Inventory> findByName(String name);

}
