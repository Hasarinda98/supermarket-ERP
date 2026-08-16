package com.bci.productcrud.service;

import com.bci.productcrud.model.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllPayments();

    Payment getPaymentById(Long id);

    Payment savePayment(Payment payment);

    void deletePayment(Long id);
}