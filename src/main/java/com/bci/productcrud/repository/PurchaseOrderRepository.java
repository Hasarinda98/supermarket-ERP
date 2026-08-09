package com.bci.productcrud.repository;

import com.bci.productcrud.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {
}