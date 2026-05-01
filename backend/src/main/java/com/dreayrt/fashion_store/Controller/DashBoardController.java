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
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private VisitLogRepository visitLogRepository;

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
    public String admin() {
        return "dashboard/admin";
    }

    @GetMapping("/api/stats")
    @ResponseBody
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalUsers = taiKhoanRepository.countByVaiTro("User");
        if (totalUsers == 0) totalUsers = taiKhoanRepository.count(); // Fallback if no roles defined yet
        
        long totalProducts = sanPhamRepository.count();
        
        BigDecimal revenue = orderRepository.sumTotalRevenue();
        if (revenue == null) revenue = BigDecimal.ZERO;
        
        long totalVisitors = visitLogRepository.count(); 
        List<VisitLog> logs = visitLogRepository.findTop10ByOrderByAccessTimeDesc();
        System.out.println("LOG ACCESS: "+logs);
        System.out.println("LOG username ACCESS: "+logs.get(0).getUsername());
        System.out.println("LOG accesstime ACCESS: "+logs.get(0).getAccessTime());
        System.out.println("LOG place ACCESS: "+logs.get(0).getPlaceVisit());

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<Map<String, Object>> recentLogs = new ArrayList<>();
        for (VisitLog log : logs) {
            Map<String, Object> item = new HashMap<>();
            String name = "Khách";
            if (log.getUsername() != null){
                name = log.getUsername();
            }
            item.put("username", name);
            item.put("accessTime", sdf.format(log.getAccessTime()));
            item.put("isMember", log.getUsername() != null);
            item.put("place", log.getPlaceVisit());
            recentLogs.add(item);
        }
        
        stats.put("totalUsers", totalUsers);
        stats.put("totalProducts", totalProducts);
        stats.put("totalRevenue", revenue);
        stats.put("totalVisitors", totalVisitors);
        stats.put("recentLogs", recentLogs);
        
        return stats;
    }
}
