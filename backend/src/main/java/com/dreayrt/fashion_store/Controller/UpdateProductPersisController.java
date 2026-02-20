package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.AddProductPersisRequest;
import com.dreayrt.fashion_store.DTOs.UpdateProductRequest;
import com.dreayrt.fashion_store.Model.Entities.Kho;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Service.PersistenceService;
import com.dreayrt.fashion_store.Service.ProductsService;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String reLoadDataUpdateProductPersis(Model model){
        List<SanPham> lstSanPham = productsService.GetSanPhamList();
        List<Kho>lstKho=persistenceService.getSanPhamKhoList();
        List<SanPhamSize>lstSize=sanPhamSizeRepository.findAll();
        model.addAttribute("lstSanPham", lstSanPham);
        model.addAttribute("lstKho", lstKho);
        model.addAttribute("lstSize", lstSize);

        if (!model.containsAttribute("updateProductRequest")) {
            model.addAttribute("updateProductRequest", new UpdateProductRequest());
        }
        return "pages/updateProducts";
    }
    @PostMapping("/pages/updateProducts")
    public String UpdateProduct(@Valid @ModelAttribute UpdateProductRequest req, BindingResult bindingResult, Model model){
        model.addAttribute("lstSanPham", productsService.GetSanPhamList());
        model.addAttribute("lstKho", persistenceService.getSanPhamKhoList());
        model.addAttribute("lstSize", sanPhamSizeRepository.findAll());
        if (bindingResult.hasErrors()) {
            model.addAttribute("upDateProError",true );
            model.addAttribute("openModal",true);
            return "pages/updateProducts";
        }

        productsService.updateProduct(req);
        model.addAttribute("upDateProSuccess",true);
        return "pages/updateProducts";
    }


}
