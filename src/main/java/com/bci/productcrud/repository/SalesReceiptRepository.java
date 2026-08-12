package com.bci.productcrud.repository;

import com.bci.productcrud.model.SalesReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesReceiptRepository
        extends JpaRepository<SalesReceipt, Long> {

    List<SalesReceipt> findByCashierId(Long cashierId);
}