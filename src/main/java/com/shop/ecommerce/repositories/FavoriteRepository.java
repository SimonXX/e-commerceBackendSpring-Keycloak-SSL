package com.shop.ecommerce.repositories;

import com.shop.ecommerce.entities.Favorite;
import com.shop.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> getFavoritesByUser(User u);
    Favorite save(Favorite favorite);
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.id = :favoriteId")
    void deleteById(Long favoriteId);
}
