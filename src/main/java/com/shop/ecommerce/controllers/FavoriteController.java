package com.shop.ecommerce.controllers;

import com.shop.ecommerce.entities.Favorite;
import com.shop.ecommerce.entities.User;
import com.shop.ecommerce.services.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;


    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }


    @DeleteMapping("/favorite/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody @Valid Favorite request){
        Favorite addedFavorite = favoriteService.addFavorite(request);
        return new ResponseEntity<>(addedFavorite, HttpStatus.CREATED);
    }

    @PostMapping("/view/{favorite}")
    public ResponseEntity<List<Favorite>> getUserFavorites(@RequestBody @Valid User user){
        List<Favorite> userFavorites = favoriteService.getFavorites(user);
        return new ResponseEntity<>(userFavorites, HttpStatus.OK);
    }


}
