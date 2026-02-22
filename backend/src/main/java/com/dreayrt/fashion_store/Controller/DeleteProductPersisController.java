package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Service.ProductsService;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DeleteProductPersisController {
    private final SanPhamSizeRepository sanPhamSizeRepository;

    public DeleteProductPersisController(SanPhamSizeRepository sanPhamSizeRepository) {
        this.sanPhamSizeRepository = sanPhamSizeRepository;
    }
    @Autowired
    private ProductsService productsService;

    @GetMapping("/pages/deleteProducts")
    public String removeProductPersis(Model model){
        List<SanPhamSize>lstSize=sanPhamSizeRepository.findAll();
        model.addAttribute("lstSize", lstSize);
        return "pages/deleteProducts";
    }
    @PostMapping("/pages/deleteProducts")
    public String deleteProductPersis(@RequestParam String maSanPham,@RequestParam String size, Model model){
       try{
           productsService.deleteSanPham(maSanPham,size);
           model.addAttribute("DeleteSuccess",true);
       }catch(Exception e){
           model.addAttribute("DeleteError",true);
       }
        List<SanPhamSize>lstSize=sanPhamSizeRepository.findAll();
        model.addAttribute("lstSize", lstSize);
        return "pages/deleteProducts";
    }
}
