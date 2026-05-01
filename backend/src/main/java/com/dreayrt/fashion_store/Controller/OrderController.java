package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.Order;
import com.dreayrt.fashion_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    
    @Value("${app.r2.public-url}")
    private String r2PublicUrl;

    @GetMapping("/pages/order")
    public String Order(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Order> orders=orderRepository.findAllByTaiKhoan_Username(username);
        model.addAttribute("orders", orders);
        model.addAttribute("r2PublicUrl", r2PublicUrl);
        return "pages/order";
    }

    @GetMapping("/pages/success")
    public String showSuccessPage() {
        return "pages/success";
    }

}
