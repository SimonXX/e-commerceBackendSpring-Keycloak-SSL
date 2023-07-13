package com.shop.ecommerce.services;

import com.shop.ecommerce.entities.Product;
import com.shop.ecommerce.repositories.ProductRepository;
import com.shop.ecommerce.support.exceptions.BarCodeAlreadyExistException;
import com.shop.ecommerce.support.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public void updateProduct(int id, int quantity, String name, String description, String image, double price) throws ProductNotFoundException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());

        product.setQuantity(quantity);
        product.setName(name);
        product.setDescription(description);
        product.setImage(image);
        product.setPrice(price);

        productRepository.save(product);
    }

    @Transactional(readOnly = false)
    public void addProduct(Product product) throws BarCodeAlreadyExistException {
        if ( product.getBarCode() != null && productRepository.existsByBarCode(product.getBarCode()) ) {
            throw new BarCodeAlreadyExistException();
        }
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> showAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> showAllProducts(int pageNumber, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productRepository.findAll(paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }


    @Transactional(readOnly = false)
    public Product editProduct(Product p){

        return productRepository.save(p);
    }


    @Transactional(readOnly = true)
    public List<Product> showProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }


    @Transactional(readOnly = true)
    public List<Product> showProductsByBarCode(String barCode) {
        return productRepository.findByBarCode(barCode);
    }


    public Product getProductById(Long productId) {

        return productRepository.findById(productId);

    }
}
