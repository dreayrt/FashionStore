package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.DTOs.CartRequest;
import com.dreayrt.fashion_store.Model.Entities.ShoppingCartDetail;
import com.dreayrt.fashion_store.Service.ShoppingCartService;
import com.dreayrt.fashion_store.repository.ShoppingCartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ShoppingCartUpdate")
public class ApiAddProductToCart {
    @Autowired
    private ShoppingCartDetailRepository spd;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping
    public String getShoppingCartDetail(
            @RequestParam String productId
            , @RequestParam String size
            , Authentication authentication) {
        String userName=authentication.getName();
        shoppingCartService.addOrUpdate(userName,productId,size);
        return "OK" ;
    }
    @PatchMapping
    public String updateShoppingCartDetail(Authentication authentication, @RequestBody CartRequest request) {
        String userName=authentication.getName();
        if("plus".equals(request.getAction())){
            shoppingCartService.incress(userName,request.getProductId(),request.getSize());
        }else if("sub".equals(request.getAction())){
            shoppingCartService.decress(userName,request.getProductId(),request.getSize());
        }
        return "OK" ;
    }




}
