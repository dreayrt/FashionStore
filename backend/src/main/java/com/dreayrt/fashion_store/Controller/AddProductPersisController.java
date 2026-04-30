package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.AddProductPersisRequest;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.AddProductFlow;
import com.dreayrt.fashion_store.Service.ProductsService;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddProductPersisController {
    private static final Logger log = LoggerFactory.getLogger(AddProductPersisController.class);

    @Autowired
    private AddProductFlow addProductFlow;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @GetMapping("/pages/addProducts")
    public String addProductPersis(Model model) {
        if (!model.containsAttribute("AddProductPersisRequest")) {
            model.addAttribute("AddProductPersisRequest", new AddProductPersisRequest());
        }
        return "pages/addProducts";
    }

    @PostMapping(value = "/pages/addProducts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String AddProductPersis(
            @Valid @ModelAttribute("AddProductPersisRequest") AddProductPersisRequest addProductPersisRequest,
            BindingResult bindingResult,
            Model model,
            Authentication authentication) {

        boolean isNew = productsService.findByMaSanPham(addProductPersisRequest.getMaSanPham().trim()).isEmpty();
        if (isNew) {
            if (addProductPersisRequest.getAnhChinh() == null || addProductPersisRequest.getAnhChinh().isEmpty()) {
                bindingResult.rejectValue("anhChinh", "anhChinh.required", "Main image is required");
            }
            if (addProductPersisRequest.getAnhChiTiet1() == null || addProductPersisRequest.getAnhChiTiet1().isEmpty()) {
                bindingResult.rejectValue("anhChiTiet1", "anhChiTiet1.required", "Detail image 1 is required");
            }
            if (addProductPersisRequest.getAnhChiTiet2() == null || addProductPersisRequest.getAnhChiTiet2().isEmpty()) {
                bindingResult.rejectValue("anhChiTiet2", "anhChiTiet2.required", "Detail image 2 is required");
            }
        }

        boolean existsMaSP = productsService.findByMaSanPham(addProductPersisRequest.getMaSanPham().trim()).isPresent();
        boolean existsSize = sanPhamSizeRepository
                .findBySanPham_MaSanPhamAndSize(addProductPersisRequest.getMaSanPham().trim(), addProductPersisRequest.getSize())
                .isPresent();
        if (existsMaSP && existsSize) {
            bindingResult.rejectValue("maSanPham", "duplicate", "Product with this size already exists");
        }

        boolean duplicate = productsService.isDuplicateTenSanPhamForCreate(addProductPersisRequest.getTenSanPham().trim());

        if (duplicate) {
            bindingResult.rejectValue("tenSanPham", "duplicate", "Product name already exists");
        }

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors when adding product: {}", bindingResult.getAllErrors());
            model.addAttribute("AddProductPersisRequestFailed", true);
            return "pages/addProducts";
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Add product attempted without authenticated user");
            model.addAttribute("AddProductPersisRequestFailed", true);
            model.addAttribute("errorMessage", "You need to login before adding products");
            return "pages/addProducts";
        }

        TaiKhoan nguoiCapNhat = taiKhoanRepository.findByUsername(authentication.getName()).orElse(null);
        if (nguoiCapNhat == null) {
            model.addAttribute("AddProductPersisRequestFailed", true);
            model.addAttribute("errorMessage", "Cannot resolve current user");
            return "pages/addProducts";
        }

        try {
            addProductFlow.addProduct(addProductPersisRequest, nguoiCapNhat);
            model.addAttribute("AddProductPersisRequestSuccess", true);
        } catch (RuntimeException e) {
            log.error("Add product failed", e);
            model.addAttribute("AddProductPersisRequestFailed", true);
            model.addAttribute("MaSPError", "Product already exists");
            return "pages/addProducts";
        }
        return "pages/addProducts";
    }
}
