package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.Order;
import com.dreayrt.fashion_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/pages/order")
    public String Order(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Order> orders=orderRepository.findAllByTaiKhoan_Username(username);
        model.addAttribute("orders", orders);
        return "pages/order";
    }

    @GetMapping("/pages/success")
    public String showSuccessPage() {
        return "pages/success";
    }

}
