package com.bci.productcrud.controller;

import com.bci.productcrud.model.SalesReceipt;
import com.bci.productcrud.model.SalesReceiptItem;
import com.bci.productcrud.model.User;
import com.bci.productcrud.repository.ProductRepository;
import com.bci.productcrud.repository.UserRepository;
import com.bci.productcrud.service.SalesReceiptService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
@RequestMapping("/sales")
public class SalesReceiptController {

    private final SalesReceiptService salesReceiptService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SalesReceiptController(
            SalesReceiptService salesReceiptService,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.salesReceiptService = salesReceiptService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listSales(Model model) {

        model.addAttribute(
                "salesReceipts",
                salesReceiptService.getAllSalesReceipts()
        );

        return "sales/list";
    }

    @GetMapping("/new")
    public String newSale(Model model) {

        SalesReceipt receipt = new SalesReceipt();

        receipt.setStatus("COMPLETED");
        receipt.setDiscount(BigDecimal.ZERO);

        SalesReceiptItem item = new SalesReceiptItem();
        item.setQuantity(1);

        receipt.setItems(new ArrayList<>());
        receipt.getItems().add(item);

        model.addAttribute("salesReceipt", receipt);

        loadFormData(model);

        return "sales/form";
    }

    @PostMapping("/save")
    public String saveSale(
            @ModelAttribute("salesReceipt")
            SalesReceipt salesReceipt,
            Model model) {

        try {

            /*
             * Reload cashier from DB.
             */
            if (salesReceipt.getCashier() == null
                    || salesReceipt.getCashier().getId() == null) {

                throw new RuntimeException(
                        "Please select a cashier."
                );
            }

            User cashier = userRepository
                    .findById(salesReceipt.getCashier().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Cashier not found."
                            )
                    );

            salesReceipt.setCashier(cashier);

            /*
             * Reload products from DB and use
             * the actual product selling price.
             */
            if (salesReceipt.getItems() == null
                    || salesReceipt.getItems().isEmpty()) {

                throw new RuntimeException(
                        "Please add a product."
                );
            }

            for (SalesReceiptItem item :
                    salesReceipt.getItems()) {

                if (item.getProduct() == null
                        || item.getProduct().getId() == null) {

                    throw new RuntimeException(
                            "Please select a product."
                    );
                }

                var product = productRepository
                        .findById(item.getProduct().getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found."
                                )
                        );

                item.setProduct(product);

                /*
                 * Product uses Double sellingPrice,
                 * while SalesReceiptItem uses BigDecimal.
                 */
                item.setUnitPrice(
                        BigDecimal.valueOf(
                                product.getSellingPrice()
                        )
                );
            }

            salesReceiptService.saveSalesReceipt(
                    salesReceipt
            );

            return "redirect:/sales";

        } catch (RuntimeException ex) {

            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            loadFormData(model);

            return "sales/form";
        }
    }

    private void loadFormData(Model model) {

        model.addAttribute(
                "products",
                productRepository.findAll()
        );

        model.addAttribute(
                "cashiers",
                userRepository.findAll()
        );
    }
}