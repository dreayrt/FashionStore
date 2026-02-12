package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.Kho;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Service.PersistenceService;
import com.dreayrt.fashion_store.Service.ProductsService;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UpdateProductPersisController {

    private final ProductsService productsService;
    private final PersistenceService persistenceService;
    private final SanPhamSizeRepository sanPhamSizeRepository;

    public UpdateProductPersisController(ProductsService productsService, PersistenceService persistenceService, SanPhamSizeRepository sanPhamSizeRepository) {
        this.productsService = productsService;
        this.persistenceService = persistenceService;
        this.sanPhamSizeRepository = sanPhamSizeRepository;
    }

    @GetMapping("/pages/updateProducts")
    public String updateProductPersis(Model model){
        List<SanPham> lstSanPham = productsService.GetSanPhamList();
        List<Kho>lstKho=persistenceService.getSanPhamKhoList();
        List<SanPhamSize>lstSize=sanPhamSizeRepository.findAll();
        model.addAttribute("lstSanPham", lstSanPham);
        model.addAttribute("lstKho", lstKho);
        model.addAttribute("lstSize", lstSize);
        return "pages/updateProducts";
    }
}
