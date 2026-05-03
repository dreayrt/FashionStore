package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.Model.Entities.Order;
import com.dreayrt.fashion_store.Service.OrderService;
import com.dreayrt.fashion_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class ApiStaff {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;
    @GetMapping("/loadData")
    public List<Order> loadData(){
        List<Order> orders = orderRepository.findAll();
        System.out.println("list don hang: "+orders);
       return orders;
    }
    @PatchMapping("/order/{id}/confirm")
    public ResponseEntity<?> confirmOrder(@PathVariable("id") Integer id){
        Order order= orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
        order.setTrangThai("Đã Xác Nhận");
        orderRepository.save(order);
        return ResponseEntity.ok("OK");
    }

    @PatchMapping("/order/{id}/ship")
    public ResponseEntity<?> shipOrder(@PathVariable("id") Integer id, @RequestParam(value = "duration", required = false) Integer duration){
        Order order= orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
        order.setTrangThai("Đang Giao");
        Date now = new Date();
        order.setNgayGiaoHang(now);
        
        if (duration != null) {
            order.setThoiGianGiaoDuKien(duration);
            // Tính toán ngày hoàn thành dự kiến: now + duration (giây)
            long completionTime = now.getTime() + (duration * 1000L);
            order.setNgayHoanThanhDuKien(new Date(completionTime));
        }
        
        orderRepository.save(order);
        return ResponseEntity.ok("OK");
    }
    @PatchMapping("/order/{id}/reject")
    public ResponseEntity<?> rejectOrder(@PathVariable("id") Integer id){
        orderService.cancelOrder(id);
        return ResponseEntity.ok("OK");
    }
}
