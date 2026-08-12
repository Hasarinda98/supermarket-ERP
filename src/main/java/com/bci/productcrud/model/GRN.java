package com.bci.productcrud.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods_receipts")
@Getter
@Setter
@NoArgsConstructor
public class GRN {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grn_id")
    private Long id;

    @Column(name = "grn_number", nullable = false, unique = true)
    private String grnNumber;

    @ManyToOne
    @JoinColumn(name = "po_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "received_by")
    private User receivedBy;

    @Column(name = "received_date", nullable = false)
    private LocalDate receivedDate;

    @Column(nullable = false, length = 30)
    private String status = "RECEIVED";

    @OneToMany(
            mappedBy = "grn",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GRNItem> items = new ArrayList<>();
}