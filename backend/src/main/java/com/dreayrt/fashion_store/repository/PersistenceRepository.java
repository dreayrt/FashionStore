package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Kho;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersistenceRepository extends JpaRepository<Kho, String> {
    List<Kho>findAll();
}
