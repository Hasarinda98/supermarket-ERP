package com.bci.productcrud.service;

import com.bci.productcrud.model.GRN;
import com.bci.productcrud.model.GRNItem;
import com.bci.productcrud.repository.GRNRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class GRNServiceImpl implements GRNService {

    private final GRNRepository grnRepository;

    public GRNServiceImpl(GRNRepository grnRepository) {
        this.grnRepository = grnRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GRN> getAllGRNs() {
        return grnRepository.findAll();
    }

    @Override
    public GRN saveGRN(GRN grn) {

        if (grn.getItems() != null) {

            for (GRNItem item : grn.getItems()) {

                // Connect each GRN item to its parent GRN
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