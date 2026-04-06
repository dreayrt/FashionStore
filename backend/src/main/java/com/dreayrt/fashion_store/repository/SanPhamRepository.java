package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.SanPham;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SanPhamRepository extends JpaRepository<SanPham, String> {
    @EntityGraph(attributePaths ={"sizes","sizes.kho"} )
    List<SanPham> findAll();
    @EntityGraph(attributePaths = {"sizes", "sizes.kho"})
    Optional<SanPham> findById (String id);
    boolean existsByTenSanPham(String tenSanPham);
    boolean existsByTenSanPhamAndMaSanPhamNot(String tenSanPham,String maSanPham);
    Optional<SanPham>getByMaSanPham(String maSanPham);
}
