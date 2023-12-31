package com.shop.ecommerce.controllers;

import com.shop.ecommerce.entities.Product;
import com.shop.ecommerce.services.ProductService;
import com.shop.ecommerce.support.ResponseMessage;
import com.shop.ecommerce.support.exceptions.BarCodeAlreadyExistException;
import com.shop.ecommerce.support.exceptions.ProductNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductService productService;


    @PreAuthorize("hasRole('client_admin')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Product product) {
        try {
            productService.addProduct(product);
        } catch (BarCodeAlreadyExistException e) {
            return new ResponseEntity<>(new ResponseMessage("Barcode already exist!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Added successful!"), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('client_user') or hasRole('client_admin')")
    @GetMapping
    public List<Product> getAll() {
        return productService.showAllProducts();
    }

    @PreAuthorize("hasRole('client_user') or hasRole('client_admin')")
    @GetMapping("/paged")
    public ResponseEntity getAll(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        List<Product> result = productService.showAllProducts(pageNumber, pageSize, sortBy);
        if ( result.size() <= 0 ) {
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('client_user') or hasRole('client_admin')")
    @GetMapping("/search/by_name")
    public ResponseEntity getByName(@RequestParam(required = false) String name) {
        List<Product> result = productService.showProductsByName(name);
        if ( result.size() <= 0 ) {
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('client_admin')")
    @Transactional
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product request) {
        try {
            productService.updateProduct(id, request.getQuantity(),
                                                request.getName(),
                                                    request.getDescription(), request.getImage(), request.getPrice());
            return ResponseEntity.ok("Product updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

}
