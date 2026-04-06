package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
    Optional<ShoppingCart> findByTaikhoan_Username(String username);

}
