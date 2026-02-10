package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SanPhamSizeRepository extends JpaRepository<SanPhamSize, Integer> {
    Optional<SanPhamSize> findBySanPham_MaSanPhamAndSize(String maSanPham, String size);
}
