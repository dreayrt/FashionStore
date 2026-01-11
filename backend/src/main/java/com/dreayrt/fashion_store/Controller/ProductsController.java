package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Service.ProductsService;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductsController {
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private ProductsService productsService;

    @GetMapping("/pages/products")
    private String index(Model model) {
        model.addAttribute("sanPhamList", productsService.GetSanPhamList());
        return "pages/products";
    }


}
