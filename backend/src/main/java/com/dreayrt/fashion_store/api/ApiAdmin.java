package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.Model.Entities.VisitLog;
import com.dreayrt.fashion_store.repository.OrderRepository;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import com.dreayrt.fashion_store.repository.VisitLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.dreayrt.fashion_store.Util.HashUtil;
import com.dreayrt.fashion_store.Service.AuthService;
import com.dreayrt.fashion_store.DTOs.RegisterRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard/api")
public class ApiAdmin {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private VisitLogRepository visitLogRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/stats")
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

    @GetMapping("/logs")
    @ResponseBody
    public Map<String, Object> getLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("accessTime").descending());
        Page<VisitLog> logPage;

        if (query != null && !query.trim().isEmpty()) {
            logPage = visitLogRepository.findByUsernameContainingIgnoreCaseOrPlaceVisitContainingIgnoreCase(query, query, pageable);
        } else {
            logPage = visitLogRepository.findAll(pageable);
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<Map<String, Object>> formattedLogs = new ArrayList<>();
        for (VisitLog log : logPage.getContent()) {
            Map<String, Object> item = new HashMap<>();
            String name = (log.getUsername() != null) ? log.getUsername() : "Khách";
            item.put("username", name);
            item.put("accessTime", sdf.format(log.getAccessTime()));
            item.put("isMember", log.getUsername() != null);
            item.put("place", log.getPlaceVisit());
            formattedLogs.add(item);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("content", formattedLogs);
        response.put("totalPages", logPage.getTotalPages());
        response.put("totalElements", logPage.getTotalElements());
        response.put("currentPage", logPage.getNumber());

        return response;
    }

    @GetMapping("/accounts")
    @ResponseBody
    public Map<String, Object> getAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        Page<com.dreayrt.fashion_store.Model.Entities.TaiKhoan> accountPage;

        if (query != null && !query.trim().isEmpty()) {
            accountPage = taiKhoanRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, pageable);
        } else {
            accountPage = taiKhoanRepository.findAll(pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("content", accountPage.getContent());
        response.put("totalPages", accountPage.getTotalPages());
        response.put("totalElements", accountPage.getTotalElements());
        response.put("currentPage", accountPage.getNumber());

        return response;
    }

    @PostMapping("/accounts")
    @ResponseBody
    public Map<String, Object> createAccount(@RequestBody Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();

        RegisterRequest request = new RegisterRequest();
        request.setUsername(data.get("username"));
        request.setPassword(data.get("password"));
        request.setConfirmPassword(data.get("password")); // Tự động khớp vì admin tạo
        request.setEmail(data.get("email"));
        request.setPhone(data.get("phone"));
        request.setAddress(data.get("diaChi"));

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "registerRequest");
        authService.Register(request, bindingResult);

        if (bindingResult.hasErrors()) {
            response.put("success", false);
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            response.put("message", message);
            return response;
        }

        response.put("success", true);
        response.put("message", "Tạo tài khoản thành công!");
        return response;
    }

    @PatchMapping("/accounts/{username}")
    @ResponseBody
    public Map<String, Object> updateAccount(@PathVariable String username, @RequestBody Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();
        return taiKhoanRepository.findById(username).map(account -> {
            if (data.containsKey("vaiTro")) account.setVaiTro(data.get("vaiTro"));
            if (data.containsKey("trangThai")) account.setTrangThai(data.get("trangThai"));
            
            // Password only if provided and not masked
            if (data.containsKey("password") && !data.get("password").isEmpty() && !data.get("password").equals("********")) {
                account.setPassword(com.dreayrt.fashion_store.Util.HashUtil.sha256(data.get("password")));
            }
            
            taiKhoanRepository.save(account);
            response.put("success", true);
            response.put("message", "Cập nhật tài khoản thành công!");
            return response;
        }).orElseGet(() -> {
            response.put("success", false);
            response.put("message", "Không tìm thấy tài khoản!");
            return response;
        });
    }

    @DeleteMapping("/accounts/{username}")
    @ResponseBody
    public Map<String, Object> deleteAccount(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        if (taiKhoanRepository.existsById(username)) {
            taiKhoanRepository.deleteById(username);
            response.put("success", true);
            response.put("message", "Xóa tài khoản thành công!");
        } else {
            response.put("success", false);
            response.put("message", "Không tìm thấy tài khoản!");
        }
        return response;
    }
}
