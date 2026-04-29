package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.Model.Entities.Order;
import com.dreayrt.fashion_store.Service.OrderService;
import com.dreayrt.fashion_store.Service.SePayService;
import com.dreayrt.fashion_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("payment")
public class ApiPayment {

    @Autowired
    private SePayService sePayService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Value("${sepay.webhook.token:}")
    private String webhookToken;

    @PostMapping("/create-url")
    public ResponseEntity<?> createPayUrl(@RequestParam(value = "orderId", required = true) Integer orderId) {
        System.out.println("Received orderId for SePay: " + orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        try {
            String payUrl = sePayService.createPaymentUrl(order);
            return ResponseEntity.ok(Map.of("payUrl", payUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/notify")
    public ResponseEntity<?> sepayNotify(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody Map<String, Object> body) {
        
        System.out.println("SePay Webhook received: " + body);

         if (webhookToken != null && !webhookToken.isEmpty()) {
             if (authorizationHeader == null || !authorizationHeader.equals("Apikey " + webhookToken)) {
                 return ResponseEntity.status(401).body(Map.of("success", false, "message", "Unauthorized"));
             }
         }
        
        try {
            // SePay webhook format: {"id": 123, "content": "DRY123", "transferAmount": 100000, ...}
            String content = (String) body.get("content");
            if (content != null) {
                // Try to extract order ID from content. E.g: content contains "DRY123"
                // Let's assume the content has the order ID prefixed with DRY
                int dryIndex = content.toUpperCase().indexOf("DRY");
                if (dryIndex != -1) {
                    // Extract number after DRY
                    String substring = content.substring(dryIndex + 3);
                    String orderIdStr = substring.replaceAll("[^0-9].*", ""); // Get only digits
                    
                    if (!orderIdStr.isEmpty()) {
                        Integer orderId = Integer.parseInt(orderIdStr);
                        Order order = orderRepository.findById(orderId).orElse(null);
                        if (order != null) {
                            orderService.completeSepayOrder(orderId);
                            return ResponseEntity.ok(Map.of("success", true));
                        }
                    }
                }
            }
            return ResponseEntity.ok(Map.of("success", true, "message", "Order not found or invalid content"));
        } catch (Exception e) {
            System.err.println("Error processing SePay webhook: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    @GetMapping("/check-status")
    public ResponseEntity<?> checkPaymentStatus(@RequestParam("orderId") Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            return ResponseEntity.ok(Map.of("paid", order.getDaThanhToan() != null && order.getDaThanhToan()));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Order not found"));
    }
}
