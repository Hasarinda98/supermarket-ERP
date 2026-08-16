package com.bci.productcrud.repository;

import com.bci.productcrud.model.SalesReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesReceiptItemRepository
        extends JpaRepository<SalesReceiptItem, Long> {

    List<SalesReceiptItem> findBySalesReceiptId(Long receiptId);

    List<SalesReceiptItem> findByProductId(Long productId);
}