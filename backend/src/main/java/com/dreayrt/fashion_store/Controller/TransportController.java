package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.Order;
import com.dreayrt.fashion_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransportController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/pages/transport")
        public String transport(Authentication authentication,@RequestParam("orderId") Integer orderId,Model model)
    {
        String username=authentication.getName();
        Order order= orderRepository.findByMaDonHangAndTaiKhoan_Username(orderId,username);
        model.addAttribute("recipientName", order.getTenNguoiNhan());
        model.addAttribute("recipientPhone", order.getSoDienThoai());
        model.addAttribute("recipientAddress", order.getDiaChi());
        model.addAttribute("orderId", orderId);
        model.addAttribute("order", order);
        return "pages/transport";
    }
}
