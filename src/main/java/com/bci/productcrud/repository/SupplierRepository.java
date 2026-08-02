package com.bci.productcrud.repository;
import com.bci.productcrud.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface SupplierRepository extends JpaRepository<Supplier,Long>{
    boolean existsByEmailIgnoreCase(String email);
    Optional<Supplier> findByEmailIgnoreCase(String email);
}
