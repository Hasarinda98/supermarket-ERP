package com.bci.productcrud.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_receipt_items")
@Getter
@Setter
@NoArgsConstructor
public class SalesReceiptItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private SalesReceipt salesReceipt;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {

        if (quantity != null && unitPrice != null) {
            subtotal = unitPrice.multiply(
                    BigDecimal.valueOf(quantity)
            );
        }
    }
}