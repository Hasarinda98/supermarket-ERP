package com.bci.productcrud.service;

import com.bci.productcrud.model.Payment;
import com.bci.productcrud.model.SalesReceipt;
import com.bci.productcrud.repository.PaymentRepository;
import com.bci.productcrud.repository.SalesReceiptRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final SalesReceiptRepository salesReceiptRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            SalesReceiptRepository salesReceiptRepository) {

        this.paymentRepository = paymentRepository;
        this.salesReceiptRepository = salesReceiptRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Payment savePayment(Payment payment) {

        if (payment.getSalesReceipt() == null
                || payment.getSalesReceipt().getId() == null) {

            throw new RuntimeException(
                    "Sales receipt is required"
            );
        }

        SalesReceipt receipt =
                salesReceiptRepository
                        .findById(
                                payment.getSalesReceipt().getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sales receipt not found"
                                )
                        );

        payment.setSalesReceipt(receipt);

        if (payment.getAmount() == null) {
            payment.setAmount(
                    receipt.getTotalAmount()
            );
        }

        if (payment.getPaymentMethod() == null
                || payment.getPaymentMethod().isBlank()) {

            payment.setPaymentMethod(
                    receipt.getPaymentMethod()
            );
        }

        if (payment.getStatus() == null
                || payment.getStatus().isBlank()) {

            payment.setStatus("COMPLETED");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}