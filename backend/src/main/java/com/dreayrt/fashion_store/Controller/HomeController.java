package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

    @GetMapping("/")
    public String index(Model model, Authentication authentication){
        // Nếu user đã đăng nhập và có role STAFF → redirect sang dashboard staff
        if (authentication != null && authentication.isAuthenticated()) {
            boolean isStaff = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"));

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isStaff) {
                return "redirect:/dashboard/staff";
            }

            if (isAdmin) {
                return "redirect:/dashboard/admin";
            }
        }
        model.addAttribute("sanPhamSizeList", sanPhamSizeRepository.findAll());
        return "index";
    }
}
