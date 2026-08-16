package com.bci.productcrud.service;

import com.bci.productcrud.model.Inventory;
import com.bci.productcrud.model.SalesReceipt;
import com.bci.productcrud.model.SalesReceiptItem;

import com.bci.productcrud.repository.InventoryRepository;
import com.bci.productcrud.repository.SalesReceiptRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class SalesReceiptServiceImpl
        implements SalesReceiptService {

    private final SalesReceiptRepository salesReceiptRepository;
    private final InventoryRepository inventoryRepository;

    public SalesReceiptServiceImpl(
            SalesReceiptRepository salesReceiptRepository,
            InventoryRepository inventoryRepository) {

        this.salesReceiptRepository = salesReceiptRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesReceipt> getAllSalesReceipts() {

        return salesReceiptRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SalesReceipt getSalesReceiptById(Long id) {

        return salesReceiptRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public SalesReceipt saveSalesReceipt(
            SalesReceipt salesReceipt) {

        BigDecimal total = BigDecimal.ZERO;

        if (salesReceipt.getItems() != null) {

            for (SalesReceiptItem item
                    : salesReceipt.getItems()) {

                item.setSalesReceipt(salesReceipt);

                if (item.getProduct() == null
                        || item.getProduct().getId() == null) {

                    throw new RuntimeException(
                            "Product is required"
                    );
                }

                if (item.getQuantity() == null
                        || item.getQuantity() <= 0) {

                    throw new RuntimeException(
                            "Quantity must be greater than zero"
                    );
                }

                if (item.getUnitPrice() == null) {

                    throw new RuntimeException(
                            "Unit price is required"
                    );
                }

                BigDecimal subtotal =
                        item.getUnitPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()
                                        )
                                );

                item.setSubtotal(subtotal);

                total = total.add(subtotal);

                /*
                 * ================================
                 * INVENTORY STOCK DECREASE
                 * ================================
                 */

                List<Inventory> inventoryList =
                        inventoryRepository
                                .findByProductId(
                                        item.getProduct().getId()
                                );

                if (inventoryList.isEmpty()) {

                    throw new RuntimeException(
                            "No inventory found for product: "
                                    + item.getProduct().getName()
                    );
                }

                Inventory inventory =
                        inventoryList.get(0);

                int currentStock =
                        inventory.getQuantityOnHand() == null
                                ? 0
                                : inventory.getQuantityOnHand();

                if (currentStock < item.getQuantity()) {

                    throw new RuntimeException(
                            "Insufficient stock for product: "
                                    + item.getProduct().getName()
                                    + ". Available stock: "
                                    + currentStock
                    );
                }

                inventory.setQuantityOnHand(
                        currentStock - item.getQuantity()
                );

                inventoryRepository.save(inventory);
            }
        }

        /*
         * Apply discount
         */

        BigDecimal discount =
                salesReceipt.getDiscount() == null
                        ? BigDecimal.ZERO
                        : salesReceipt.getDiscount();

        BigDecimal finalTotal =
                total.subtract(discount);

        if (finalTotal.compareTo(BigDecimal.ZERO) < 0) {
            finalTotal = BigDecimal.ZERO;
        }

        salesReceipt.setTotalAmount(finalTotal);

        if (salesReceipt.getStatus() == null
                || salesReceipt.getStatus().isBlank()) {

            salesReceipt.setStatus("COMPLETED");
        }

        return salesReceiptRepository.save(
                salesReceipt
        );
    }

    @Override
    public void deleteSalesReceipt(Long id) {

        salesReceiptRepository.deleteById(id);
    }
}