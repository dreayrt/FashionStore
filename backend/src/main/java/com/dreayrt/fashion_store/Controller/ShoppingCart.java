package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.ShoppingCartDetail;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.ShoppingCartService;
import com.dreayrt.fashion_store.repository.ShoppingCartDetailRepository;
import com.dreayrt.fashion_store.repository.ShoppingCartRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCart {
    @Autowired
    private ShoppingCartDetailRepository shoppingCartDetailRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/pages/ShoppingCart")
    public String cart(Model model, Authentication auth) {
        String username = auth.getName();
        List<ShoppingCartDetail> items =
                shoppingCartDetailRepository
                        .findByShoppingCart_Taikhoan_Username(username);
        System.out.println("ITEM SIZE: " + items.size());
        model.addAttribute("items", items);
        return "pages/ShoppingCart";
    }
}
