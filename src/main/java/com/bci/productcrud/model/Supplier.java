package com.bci.productcrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "suppliers")
@Getter @Setter @NoArgsConstructor
public class Supplier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Supplier name is required") @Size(max=100)
    @Column(nullable=false, length=100) private String name;
    @NotBlank(message="Company is required") @Size(max=120)
    @Column(nullable=false, length=120) private String company;
    @NotBlank(message="Email is required") @Email(message="Invalid email")
    @Column(nullable=false, unique=true, length=150) private String email;
    @NotBlank(message="Phone is required")
    @Pattern(regexp="^[0-9+()\\- ]{7,20}$", message="Invalid phone number")
    @Column(nullable=false, length=20) private String phone;
    @NotBlank(message="Address is required") @Size(max=255)
    @Column(nullable=false) private String address;
    @Column(updatable=false) private Instant createdAt;
    private Instant updatedAt;
    @PrePersist void create(){ createdAt=Instant.now(); updatedAt=createdAt; }
    @PreUpdate void updateTime(){ updatedAt=Instant.now(); }
}
