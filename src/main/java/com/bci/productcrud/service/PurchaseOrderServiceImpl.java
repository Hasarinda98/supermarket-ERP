package com.bci.productcrud.service;

import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.repository.PurchaseOrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl
        implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;

    public PurchaseOrderServiceImpl(
            PurchaseOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return repository.findAll();
    }

    @Override
    public PurchaseOrder savePurchaseOrder(
            PurchaseOrder purchaseOrder) {

        if (purchaseOrder.getQuantity() != null
                && purchaseOrder.getUnitPrice() != null) {

            purchaseOrder.setTotalAmount(
                    purchaseOrder.getQuantity()
                            * purchaseOrder.getUnitPrice()
            );
        }

        return repository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        repository.deleteById(id);
    }
}