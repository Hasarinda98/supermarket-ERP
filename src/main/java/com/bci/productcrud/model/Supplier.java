package com.bci.productcrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long id;

    @NotBlank(message = "Supplier name is required")
    @Size(max = 100)
    @Column(name = "supplier_name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Contact person is required")
    @Size(max = 100)
    @Column(name = "contact_person", nullable = false, length = 100)
    private String contactPerson;

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "^[0-9+()\\- ]{7,20}$",
            message = "Invalid phone number"
    )
    @Column(nullable = false, length = 20)
    private String phone;

    @NotBlank(message = "Address is required")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String address;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Size(max = 255)
    @Column(name = "bank_details", length = 255)
    private String bankDetails;

    @NotBlank(message = "Status is required")
    @Column(nullable = false, length = 20)
    private String status = "ACTIVE";
}