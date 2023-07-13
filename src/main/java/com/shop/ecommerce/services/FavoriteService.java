package com.shop.ecommerce.services;


import com.shop.ecommerce.entities.Favorite;
import com.shop.ecommerce.entities.Product;
import com.shop.ecommerce.entities.User;
import com.shop.ecommerce.repositories.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;



    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public void deleteFavorite(Long favoriteId) {

        favoriteRepository.deleteById(favoriteId);
    }

    @Transactional(readOnly = true)
    public List<Favorite> getFavorites(User u){
        return favoriteRepository.getFavoritesByUser(u);
    }

    public Favorite addFavorite(Favorite request){
        Product productId = request.getProduct();
        User userId = request.getUser();

        Favorite favorite = new Favorite();
        favorite.setProduct(productId);
        favorite.setUser(userId);

        return favoriteRepository.save(favorite);

    }

}
