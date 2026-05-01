package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.Model.Entities.Order;
import com.dreayrt.fashion_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderTaskService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Chạy mỗi phút để kiểm tra và hoàn thành các đơn hàng đã đến nơi dự kiến.
     */
    @Scheduled(fixedRate = 60000) // 60,000 ms = 1 minute
    @Transactional
    public void autoCompleteOrders() {
        Date now = new Date();
        
        // Lấy tất cả các đơn hàng đang giao
        List<Order> shippingOrders = orderRepository.findAll().stream()
                .filter(o -> "Đang Giao".equals(o.getTrangThai()) && o.getNgayHoanThanhDuKien() != null)
                .collect(Collectors.toList());
        
        for (Order order : shippingOrders) {
            if (order.getNgayHoanThanhDuKien().before(now)) {
                System.out.println("Auto-completing order: #" + order.getMaDonHang());
                order.setTrangThai("Hoàn thành");
                orderRepository.save(order);
            }
        }
    }
}
