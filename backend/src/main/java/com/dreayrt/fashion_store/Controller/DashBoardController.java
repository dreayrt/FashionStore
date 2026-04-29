package com.dreayrt.fashion_store.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.dreayrt.fashion_store.repository.OrderRepository;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private SanPhamRepository sanPhamRepository;
    
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

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
}
