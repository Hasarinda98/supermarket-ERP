package com.bci.productcrud.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales_receipts")
@Getter
@Setter
@NoArgsConstructor
public class SalesReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long id;

    @Column(name = "receipt_number", nullable = false, unique = true)
    private String receiptNumber;

    @ManyToOne
    @JoinColumn(name = "cashier_id", nullable = false)
    private User cashier;

    @Column(name = "receipt_date", nullable = false)
    private LocalDateTime receiptDate;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "payment_method", length = 30)
    private String paymentMethod;

    @Column(nullable = false, length = 30)
    private String status = "COMPLETED";

    @OneToMany(
            mappedBy = "salesReceipt",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SalesReceiptItem> items = new ArrayList<>();

    @PrePersist
    public void setDefaultDate() {
        if (receiptDate == null) {
            receiptDate = LocalDateTime.now();
        }
    }
}