package com.bci.productcrud.service;
import com.bci.productcrud.exception.*;
import com.bci.productcrud.model.Supplier;
import com.bci.productcrud.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional
public class SupplierServiceImpl implements SupplierService {
 private final SupplierRepository supplierRepository; private final ProductRepository productRepository;
 public Supplier create(Supplier s){ String e=norm(s.getEmail()); if(supplierRepository.existsByEmailIgnoreCase(e)) throw new DuplicateSupplierEmailException("Supplier email already exists"); s.setId(null); s.setEmail(e); return supplierRepository.save(s); }
 @Transactional(readOnly=true) public List<Supplier> findAll(){return supplierRepository.findAll();}
 @Transactional(readOnly=true) public Supplier findById(Long id){return supplierRepository.findById(id).orElseThrow(()->new SupplierNotFoundException("Supplier not found: "+id));}
 public Supplier update(Long id,Supplier r){Supplier s=findById(id); String e=norm(r.getEmail()); supplierRepository.findByEmailIgnoreCase(e).filter(x->!x.getId().equals(id)).ifPresent(x->{throw new DuplicateSupplierEmailException("Supplier email already exists");}); s.setName(r.getName());s.setCompany(r.getCompany());s.setEmail(e);s.setPhone(r.getPhone());s.setAddress(r.getAddress());return supplierRepository.save(s);}
 public void delete(Long id){Supplier s=findById(id); if(productRepository.existsBySupplierId(id)) throw new SupplierInUseException("Supplier has assigned products"); supplierRepository.delete(s);}
 private String norm(String e){return e==null?null:e.trim().toLowerCase();}
}
