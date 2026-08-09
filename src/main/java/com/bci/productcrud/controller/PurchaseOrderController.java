package com.bci.productcrud.controller;

import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.service.PurchaseOrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    public PurchaseOrderController(PurchaseOrderService service) {
        this.service = service;
    }

    @GetMapping
    public String listPurchaseOrders(Model model) {
        model.addAttribute(
                "purchaseOrders",
                service.getAllPurchaseOrders()
        );

        return "purchase-order/list";
    }

    @GetMapping("/new")
    public String newPurchaseOrder(Model model) {
        PurchaseOrder po = new PurchaseOrder();
        po.setStatus("PENDING");

        model.addAttribute("purchaseOrder", po);

        return "purchase-order/form";
    }

    @PostMapping("/save")
    public String savePurchaseOrder(
            @ModelAttribute PurchaseOrder purchaseOrder) {

        service.savePurchaseOrder(purchaseOrder);

        return "redirect:/purchase-orders";
    }

    @GetMapping("/edit/{id}")
    public String editPurchaseOrder(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "purchaseOrder",
                service.getPurchaseOrderById(id)
        );

        return "purchase-order/form";
    }

    @GetMapping("/delete/{id}")
    public String deletePurchaseOrder(
            @PathVariable Long id) {

        service.deletePurchaseOrder(id);

        return "redirect:/purchase-orders";
    }
}