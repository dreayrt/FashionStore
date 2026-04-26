package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.DisplayItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisplayItemRepository extends JpaRepository<DisplayItem,Integer> {

    @EntityGraph(attributePaths = {
            "sanPhamSize",
            "sanPhamSize.sanPham"
    })
    DisplayItem findById(int id);
}
