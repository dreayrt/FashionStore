package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetailProductController {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping("/pages/product-detail")
    public String ProductDetail(@RequestParam("id") String id, Model model ) {
        SanPham product = sanPhamRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return"pages/productDetail";
    }
}
