package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductsController {
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

    @GetMapping("/pages/products")
    private String index(Model model) {
        model.addAttribute("sanPhamSizeList", sanPhamSizeRepository.findAll());
        return "pages/products";
    }


}
