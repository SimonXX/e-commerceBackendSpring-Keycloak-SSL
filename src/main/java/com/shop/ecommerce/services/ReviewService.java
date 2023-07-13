package com.shop.ecommerce.services;

import com.shop.ecommerce.entities.Product;
import com.shop.ecommerce.entities.Review;
import com.shop.ecommerce.entities.User;
import com.shop.ecommerce.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviews(Product product) {
        return reviewRepository.getReviewsByProduct(product);
    }
    public Review addReview(Review request) {
        // Recupera l'ID del prodotto e dell'utente dalla richiesta
        Product productId = request.getProduct();
        User userId = request.getUser();

        // Verifica se il prodotto e l'utente esistono




        // Crea una nuova istanza di Review e imposta i valori
        Review review = new Review();
        review.setProduct(productId);
        review.setUser(userId);
        review.setComment(request.getComment());
        review.setRating(request.getRating());

        // Salva la recensione nel repository delle recensioni
        return reviewRepository.save(review);
    }
}