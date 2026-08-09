package com.bci.productcrud.service;

import com.bci.productcrud.model.GRN;
import com.bci.productcrud.repository.GRNRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GRNServiceImpl implements GRNService {

    private final GRNRepository repository;

    public GRNServiceImpl(GRNRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GRN> getAllGRNs() {
        return repository.findAll();
    }

    @Override
    public GRN saveGRN(GRN grn) {

        if (grn.getReceivedQuantity() != null
                && grn.getUnitPrice() != null) {

            grn.setTotalAmount(
                    grn.getReceivedQuantity()
                            * grn.getUnitPrice()
            );
        }

        return repository.save(grn);
    }

    @Override
    public GRN getGRNById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteGRN(Long id) {
        repository.deleteById(id);
    }
}