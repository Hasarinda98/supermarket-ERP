package com.bci.productcrud.service;

import com.bci.productcrud.model.GRN;
import com.bci.productcrud.model.GRNItem;
import com.bci.productcrud.model.Inventory;
import com.bci.productcrud.model.Location;
import com.bci.productcrud.repository.GRNRepository;
import com.bci.productcrud.repository.InventoryRepository;
import com.bci.productcrud.repository.LocationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GRNServiceImpl implements GRNService {

    private final GRNRepository grnRepository;
    private final InventoryRepository inventoryRepository;
    private final LocationRepository locationRepository;

    public GRNServiceImpl(
            GRNRepository grnRepository,
            InventoryRepository inventoryRepository,
            LocationRepository locationRepository) {

        this.grnRepository = grnRepository;
        this.inventoryRepository = inventoryRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GRN> getAllGRNs() {
        return grnRepository.findAll();
    }

    @Override
    public GRN saveGRN(GRN grn) {

        // Get default inventory location
        Location location = locationRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "No inventory location found. Please add a location first."
                        )
                );

        if (grn.getItems() != null) {

            for (GRNItem item : grn.getItems()) {

                // Connect GRN item to parent GRN
                item.setGrn(grn);

                // Calculate subtotal
                if (item.getReceivedQuantity() != null
                        && item.getUnitPrice() != null) {

                    BigDecimal subtotal =
                            item.getUnitPrice()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    item.getReceivedQuantity()
                                            )
                                    );

                    item.setSubtotal(subtotal);
                }

                // =========================
                // UPDATE INVENTORY
                // =========================

                if (item.getProduct() != null
                        && item.getProduct().getId() != null
                        && item.getReceivedQuantity() != null) {

                    Long productId = item.getProduct().getId();

                    Optional<Inventory> existingInventory =
                            inventoryRepository
                                    .findByProductIdAndLocationId(
                                            productId,
                                            location.getId()
                                    );

                    Inventory inventory;

                    if (existingInventory.isPresent()) {

                        inventory = existingInventory.get();

                        int currentQuantity =
                                inventory.getQuantityOnHand() == null
                                        ? 0
                                        : inventory.getQuantityOnHand();

                        inventory.setQuantityOnHand(
                                currentQuantity
                                        + item.getReceivedQuantity()
                        );

                    } else {

                        inventory = new Inventory();

                        inventory.setProduct(item.getProduct());
                        inventory.setLocation(location);
                        inventory.setQuantityOnHand(
                                item.getReceivedQuantity()
                        );
                    }

                    inventoryRepository.save(inventory);
                }
            }
        }

        return grnRepository.save(grn);
    }

    @Override
    @Transactional(readOnly = true)
    public GRN getGRNById(Long id) {

        return grnRepository.findById(id)
                .orElse(null);
    }

    @Override
    public void deleteGRN(Long id) {

        grnRepository.deleteById(id);
    }
}