package com.bci.productcrud.controller;

import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.model.PurchaseOrderItem;
import com.bci.productcrud.repository.ProductRepository;
import com.bci.productcrud.repository.SupplierRepository;
import com.bci.productcrud.service.PurchaseOrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService service;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderController(
            PurchaseOrderService service,
            SupplierRepository supplierRepository,
            ProductRepository productRepository) {

        this.service = service;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
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

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setPoDate(LocalDate.now());
        purchaseOrder.setStatus("PENDING");

        // Add one empty item to the PO
        PurchaseOrderItem item = new PurchaseOrderItem();
        item.setPurchaseOrder(purchaseOrder);

        purchaseOrder.getItems().add(item);

        model.addAttribute(
                "purchaseOrder",
                purchaseOrder
        );

        model.addAttribute(
                "suppliers",
                supplierRepository.findAll()
        );

        model.addAttribute(
                "products",
                productRepository.findAll()
        );

        return "purchase-order/form";
    }

    @PostMapping("/save")
    public String savePurchaseOrder(
            @ModelAttribute("purchaseOrder")
            PurchaseOrder purchaseOrder) {

        service.savePurchaseOrder(purchaseOrder);

        return "redirect:/purchase-orders";
    }

    @GetMapping("/edit/{id}")
    public String editPurchaseOrder(
            @PathVariable Long id,
            Model model) {

        PurchaseOrder purchaseOrder =
                service.getPurchaseOrderById(id);

        if (purchaseOrder == null) {
            return "redirect:/purchase-orders";
        }

        // Make sure edit form always has at least one item
        if (purchaseOrder.getItems() == null
                || purchaseOrder.getItems().isEmpty()) {

            PurchaseOrderItem item =
                    new PurchaseOrderItem();

            item.setPurchaseOrder(purchaseOrder);

            purchaseOrder.getItems().add(item);
        }

        model.addAttribute(
                "purchaseOrder",
                purchaseOrder
        );

        model.addAttribute(
                "suppliers",
                supplierRepository.findAll()
        );

        model.addAttribute(
                "products",
                productRepository.findAll()
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