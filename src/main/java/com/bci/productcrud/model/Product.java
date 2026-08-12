package com.bci.productcrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(name = "product_name", nullable = false)
    private String name;

    private String category;

    private String brand;

    private String size;

    private String color;

    @NotBlank(message = "Barcode is required")
    @Column(nullable = false, unique = true)
    private String barcode;

    @NotNull(message = "Purchase price is required")
    @PositiveOrZero
    @Column(name = "purchase_price", nullable = false)
    private Double purchasePrice;

    @NotNull(message = "Selling price is required")
    @PositiveOrZero
    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice;

    @Column(nullable = false)
    private String status = "ACTIVE";
}