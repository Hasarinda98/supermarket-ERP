package com.bci.productcrud.service;

import com.bci.productcrud.exception.DuplicateBarcodeException;
import com.bci.productcrud.exception.ProductNotFoundException;
import com.bci.productcrud.model.Product;
import com.bci.productcrud.repository.ProductRepository;
import com.bci.productcrud.repository.SupplierRepository;
import com.bci.productcrud.exception.SupplierNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public Product create(Product product) {
        if (productRepository.existsByBarcode(product.getBarcode())) {
            throw new DuplicateBarcodeException("A product with barcode " + product.getBarcode() + " already exists");
        }
        product.setId(null);
        product.setSupplier(resolveSupplier(product));
        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Product findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with barcode " + barcode));
    }

    @Override
    public Product update(Long id, Product request) {
        Product product = findById(id);

        productRepository.findByBarcode(request.getBarcode())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateBarcodeException("A product with barcode " + request.getBarcode() + " already exists");
                });

        product.setBarcode(request.getBarcode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setSupplier(resolveSupplier(request));
        return productRepository.save(product);
    }

    private com.bci.productcrud.model.Supplier resolveSupplier(Product product) {
        if (product.getSupplier() == null || product.getSupplier().getId() == null) return null;
        return supplierRepository.findById(product.getSupplier().getId())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found: " + product.getSupplier().getId()));
    }

    @Override
    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
