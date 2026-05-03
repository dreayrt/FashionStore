package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.Advertisement;
import com.dreayrt.fashion_store.Service.AdvertisementService;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import com.dreayrt.fashion_store.Service.VisitLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class HomeController {
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

    @Autowired
    private VisitLogService visitLogService;

    @Autowired
    private AdvertisementService advertisementService;
    @GetMapping("/")
    public String index(Model model, Authentication authentication,  HttpServletRequest request){
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
        List<Advertisement> ads = advertisementService.getActiveAds();
        model.addAttribute("adsList", ads);
        model.addAttribute("sanPhamSizeList", sanPhamSizeRepository.findAll());
        return "index";
    }
}
