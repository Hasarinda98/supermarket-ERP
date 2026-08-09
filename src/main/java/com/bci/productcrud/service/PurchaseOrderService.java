package com.bci.productcrud.service;

import com.bci.productcrud.model.PurchaseOrder;
import java.util.List;

public interface PurchaseOrderService {

    List<PurchaseOrder> getAllPurchaseOrders();

    PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder);

    PurchaseOrder getPurchaseOrderById(Long id);

    void deletePurchaseOrder(Long id);
}