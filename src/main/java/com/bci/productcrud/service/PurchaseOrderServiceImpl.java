package com.bci.productcrud.service;

import com.bci.productcrud.model.PurchaseOrder;
import com.bci.productcrud.model.PurchaseOrderItem;
import com.bci.productcrud.repository.PurchaseOrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PurchaseOrderServiceImpl
        implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;

    public PurchaseOrderServiceImpl(
            PurchaseOrderRepository repository) {

        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrder> getAllPurchaseOrders() {

        return repository.findAll();
    }

    @Override
    public PurchaseOrder savePurchaseOrder(
            PurchaseOrder purchaseOrder) {

        BigDecimal total = BigDecimal.ZERO;

        if (purchaseOrder.getItems() != null) {

            for (PurchaseOrderItem item
                    : purchaseOrder.getItems()) {

                // connect item with its parent Purchase Order
                item.setPurchaseOrder(purchaseOrder);

                if (item.getQuantity() != null
                        && item.getUnitPrice() != null) {

                    BigDecimal subtotal =
                            item.getUnitPrice()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    item.getQuantity()
                                            )
                                    );

                    item.setSubtotal(subtotal);

                    total = total.add(subtotal);
                }
            }
        }

        purchaseOrder.setTotalAmount(total);

        return repository.save(purchaseOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrder getPurchaseOrderById(Long id) {

        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public void deletePurchaseOrder(Long id) {

        repository.deleteById(id);
    }
}