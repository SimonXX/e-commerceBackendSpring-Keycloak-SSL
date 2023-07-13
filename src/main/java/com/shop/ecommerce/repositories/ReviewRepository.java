package com.shop.ecommerce.repositories;

import com.shop.ecommerce.entities.Product;
import com.shop.ecommerce.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> getReviewsByProduct(Product product);
    Product findByProductId(Long id);



}