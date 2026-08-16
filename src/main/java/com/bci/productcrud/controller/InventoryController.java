package com.bci.productcrud.controller;

import com.bci.productcrud.service.InventoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(
            InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public String listInventory(Model model) {

        model.addAttribute(
                "inventoryList",
                inventoryService.getAllInventory()
        );

        return "inventory/list";
    }
}