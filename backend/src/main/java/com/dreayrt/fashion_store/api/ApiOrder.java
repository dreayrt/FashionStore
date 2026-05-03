package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.DTOs.OrderRequest;
import com.dreayrt.fashion_store.Model.Entities.Order;
import com.dreayrt.fashion_store.Model.Entities.OrderDetail;
import com.dreayrt.fashion_store.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import com.dreayrt.fashion_store.repository.OrderRepository;

import com.dreayrt.fashion_store.DTOs.OrderPreviewDTO;

import java.util.Map;

@RestController
@RequestMapping("api/Order")
public class ApiOrder {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public Map<String, Object> createOrder(Authentication authentication, @RequestBody OrderRequest request){
        Order order = orderService.createOrder(authentication, request);
        return Map.of("orderId", order.getMaDonHang());
    }

    @PostMapping("/preview")
    public OrderPreviewDTO previewOrder(Authentication authentication, @RequestBody Map<String, String> request) {
        String voucherCode = request.get("voucherCode");
        return orderService.previewOrder(authentication, voucherCode);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setTrangThai("Hoàn thành");
        orderRepository.save(order);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null && "Chờ Thanh Toán".equals(order.getTrangThai())) {
            orderService.deleteOrderFully(id);
        }
        return ResponseEntity.ok(Map.of("success", true));
    }

}
