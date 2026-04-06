package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Model.Entities.ShoppingCart;
import com.dreayrt.fashion_store.Model.Entities.ShoppingCartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartDetailRepository extends JpaRepository<ShoppingCartDetail, Integer> {
    List<ShoppingCartDetail> findByShoppingCart_Taikhoan_Username(String username);

    Optional<ShoppingCartDetail> findByShoppingCart_Taikhoan_UsernameAndSanPhamSize_MaSPSize(String username, Integer sanPhamSize);

}
