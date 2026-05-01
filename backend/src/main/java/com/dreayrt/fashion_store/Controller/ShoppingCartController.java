package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.ShoppingCartDetail;
import com.dreayrt.fashion_store.Service.ShoppingCartService;
import com.dreayrt.fashion_store.repository.ShoppingCartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {
    @Autowired
    private ShoppingCartDetailRepository shoppingCartDetailRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Value("${app.r2.public-url}")
    private String r2PublicUrl;

    @GetMapping("/pages/ShoppingCart")
    public String cart(Model model, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/pages/login";
        }

        String username = auth.getName();
        List<ShoppingCartDetail> items =
                shoppingCartDetailRepository.findByShoppingCart_Taikhoan_Username(username);
        model.addAttribute("items", items);
        model.addAttribute("r2PublicUrl", r2PublicUrl);
        return "pages/ShoppingCart";
    }
}
