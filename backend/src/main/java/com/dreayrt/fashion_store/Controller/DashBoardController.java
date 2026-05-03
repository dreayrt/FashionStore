package com.dreayrt.fashion_store.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.dreayrt.fashion_store.repository.OrderRepository;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Model.Entities.VisitLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import com.dreayrt.fashion_store.repository.VisitLogRepository;
import com.dreayrt.fashion_store.DTOs.AdvertisementDTO;
import com.dreayrt.fashion_store.Service.AdvertisementService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;
    
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

    @Autowired
    private AdvertisementService advertisementService;
    


    @GetMapping("/staff")
    public String staff(Model model) {
        long totalProducts = sanPhamRepository.count();
        long pendingOrders = orderRepository.countByTrangThai("Chờ Xác Nhận");
        long confirmedOrders = orderRepository.countByTrangThai("Đã Xác Nhận");
        
        List<SanPhamSize> sizes = sanPhamSizeRepository.findAll();
        long lowStockProducts = 0;
        for (SanPhamSize size : sizes) {
            if (size.getKho() != null && size.getKho().getSoLuong() != null && size.getKho().getSoLuong() <= 5) {
                lowStockProducts++;
            }
        }
        
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("confirmedOrders", confirmedOrders);
        model.addAttribute("lowStockProducts", lowStockProducts);
        
        return "dashboard/staff";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        if (!model.containsAttribute("adsRequest")) {
            model.addAttribute("adsRequest", new AdvertisementDTO());
        }
        return "dashboard/admin";
    }

    @PostMapping("/admin/save-ads")
    public String saveAds(@Valid @ModelAttribute("adsRequest") AdvertisementDTO adsRequest,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("activeSection", "ads");
            return "dashboard/admin";
        }

        try {
            advertisementService.createAdvertisement(adsRequest);
            redirectAttributes.addFlashAttribute("adsSuccess", true);
        } catch (Exception e) {
            model.addAttribute("adsError", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("activeSection", "ads");
            return "dashboard/admin";
        }

        return "redirect:/dashboard/admin?section=ads&success=true";
    }


}
