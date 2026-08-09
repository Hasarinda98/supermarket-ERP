package com.bci.productcrud.repository;

import com.bci.productcrud.model.GRN;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GRNRepository
        extends JpaRepository<GRN, Long> {
}