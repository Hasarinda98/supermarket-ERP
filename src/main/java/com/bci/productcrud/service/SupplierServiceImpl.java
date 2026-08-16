package com.bci.productcrud.service;

import com.bci.productcrud.exception.DuplicateSupplierEmailException;
import com.bci.productcrud.exception.SupplierNotFoundException;
import com.bci.productcrud.model.Supplier;
import com.bci.productcrud.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier create(Supplier supplier) {

        String email = normalizeEmail(supplier.getEmail());

        if (supplierRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateSupplierEmailException(
                    "Supplier email already exists"
            );
        }

        supplier.setId(null);
        supplier.setEmail(email);

        return supplierRepository.save(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Supplier findById(Long id) {

        return supplierRepository.findById(id)
                .orElseThrow(() ->
                        new SupplierNotFoundException(
                                "Supplier not found: " + id
                        )
                );
    }

    @Override
    public Supplier update(Long id, Supplier request) {

        Supplier supplier = findById(id);

        String email = normalizeEmail(request.getEmail());

        supplierRepository.findByEmailIgnoreCase(email)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateSupplierEmailException(
                            "Supplier email already exists"
                    );
                });

        supplier.setName(request.getName());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setEmail(email);
        supplier.setBankDetails(request.getBankDetails());
        supplier.setStatus(request.getStatus());

        return supplierRepository.save(supplier);
    }

    @Override
    public void delete(Long id) {

        Supplier supplier = findById(id);

        supplierRepository.delete(supplier);
    }

    private String normalizeEmail(String email) {

        return email == null
                ? null
                : email.trim().toLowerCase();
    }
}