package com.shop.ecommerce.controllers;

import com.shop.ecommerce.entities.Product;
import com.shop.ecommerce.entities.Review;
import com.shop.ecommerce.entities.User;
import com.shop.ecommerce.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PreAuthorize("hasRole('client_user')")
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody @Valid Review request) {
        Review addedReview = reviewService.addReview(request);
        return new ResponseEntity<>(addedReview, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('client_user') or hasRole('client_admin')")
    @PostMapping("/view/{product}")
    public ResponseEntity<List<Review>> getProductReviews(@RequestBody @Valid Product product) {
        List<Review> productReviews = reviewService.getReviews(product);
        return new ResponseEntity<>(productReviews, HttpStatus.OK);
    }


}