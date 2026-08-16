package com.bci.productcrud.repository;

import com.bci.productcrud.model.GRNItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GRNItemRepository
        extends JpaRepository<GRNItem, Long> {

    List<GRNItem> findByGrnId(Long grnId);

    List<GRNItem> findByProductId(Long productId);
}