package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Service.PersistenceService;
import com.dreayrt.fashion_store.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KhoController {
    @Autowired
    private PersistenceService persistenceService;
@GetMapping("/pages/kho")
public String kho(Model model){
    model.addAttribute("ListSanPhamKho",persistenceService.getSanPhamKhoList());
    return "pages/kho";
}
}
