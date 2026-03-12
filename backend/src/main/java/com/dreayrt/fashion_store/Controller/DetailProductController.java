package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.Comment;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.repository.CommentRepository;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DetailProductController {
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/pages/product-detail")
    public String ProductDetail(@RequestParam("id") String id, Model model) {
        SanPham product = sanPhamRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        List<Comment> comments=commentRepository.findBySanPham_MaSanPham(id);
        model.addAttribute("product", product);
        model.addAttribute("comments", comments);
        return "pages/productDetail";
    }
}
