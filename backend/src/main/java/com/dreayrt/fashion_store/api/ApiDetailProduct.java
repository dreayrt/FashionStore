package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-detail")
public class ApiDetailProduct {
    private final SanPhamRepository sanPhamRepository;

    public ApiDetailProduct(SanPhamRepository sanPhamRepository) {
        this.sanPhamRepository = sanPhamRepository;
    }

    @GetMapping
    public SanPham getDetatail(@RequestParam("id") String id) {
        return sanPhamRepository.findById(id).get();
    }

}



