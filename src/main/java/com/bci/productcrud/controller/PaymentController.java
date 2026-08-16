package com.bci.productcrud.controller;

import com.bci.productcrud.model.Payment;
import com.bci.productcrud.model.SalesReceipt;
import com.bci.productcrud.repository.SalesReceiptRepository;
import com.bci.productcrud.service.PaymentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final SalesReceiptRepository salesReceiptRepository;

    public PaymentController(
            PaymentService paymentService,
            SalesReceiptRepository salesReceiptRepository) {

        this.paymentService = paymentService;
        this.salesReceiptRepository = salesReceiptRepository;
    }

    @GetMapping
    public String listPayments(Model model) {

        model.addAttribute(
                "payments",
                paymentService.getAllPayments()
        );

        return "payments/list";
    }

    @GetMapping("/new")
    public String newPayment(Model model) {

        Payment payment = new Payment();

        payment.setStatus("COMPLETED");

        model.addAttribute(
                "payment",
                payment
        );

        model.addAttribute(
                "salesReceipts",
                salesReceiptRepository.findAll()
        );

        return "payments/form";
    }

    @PostMapping("/save")
    public String savePayment(
            @ModelAttribute("payment")
            Payment payment,
            Model model) {

        try {

            paymentService.savePayment(payment);

            return "redirect:/payments";

        } catch (RuntimeException ex) {

            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            model.addAttribute(
                    "salesReceipts",
                    salesReceiptRepository.findAll()
            );

            return "payments/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(
            @PathVariable Long id) {

        paymentService.deletePayment(id);

        return "redirect:/payments";
    }
}