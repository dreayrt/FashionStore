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

import java.util.Map;

@RestController
@RequestMapping("api/Order")
public class ApiOrder {
    @Autowired
    private OrderService orderService;
    @PostMapping("/create")
    public Map<String, Object> createOrder(Authentication authentication, @RequestBody OrderRequest request){
        Order order = orderService.createOrder(authentication, request);
        return Map.of("orderId", order.getMaDonHang());
    }

}
