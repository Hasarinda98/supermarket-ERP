package com.bci.productcrud.service;

import com.bci.productcrud.model.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> getAllInventory();

    Inventory getInventoryById(Long id);

    List<Inventory> getInventoryByProduct(Long productId);

    List<Inventory> getInventoryByLocation(Long locationId);
}