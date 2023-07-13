package com.shop.ecommerce.repositories;

import com.shop.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContaining(String name);
    List<Product> findByBarCode(String name);
    boolean existsByBarCode(String barCode);

    Product findById(Long id);

}
