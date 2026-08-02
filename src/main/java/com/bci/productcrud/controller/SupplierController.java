package com.bci.productcrud.controller;
import com.bci.productcrud.model.Supplier;
import com.bci.productcrud.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/suppliers") @RequiredArgsConstructor
public class SupplierController {
 private final SupplierService service;
 @PostMapping public ResponseEntity<Supplier> create(@Valid @RequestBody Supplier s){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(s));}
 @GetMapping public List<Supplier> all(){return service.findAll();}
 @GetMapping("/{id}") public Supplier one(@PathVariable Long id){return service.findById(id);}
 @PutMapping("/{id}") public Supplier update(@PathVariable Long id,@Valid @RequestBody Supplier s){return service.update(id,s);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}
