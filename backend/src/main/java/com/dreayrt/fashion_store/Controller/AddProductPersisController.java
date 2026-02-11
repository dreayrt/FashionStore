package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.AddProductPersisRequest;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.AddProductFlow;
import com.dreayrt.fashion_store.Service.ProductsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AddProductPersisController {
    private static final Logger log = LoggerFactory.getLogger(AddProductPersisController.class);
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private AddProductFlow  addProductFlow;
    @Autowired
    private ProductsService productsService;

    @GetMapping("/pages/addProducts")
    public String addProductPersis(Model model) {
        if (!model.containsAttribute("AddProductPersisRequest")) {
            model.addAttribute("AddProductPersisRequest", new AddProductPersisRequest());
        }
        return "pages/addProducts";
    }
    @PostMapping(value = "/pages/addProducts",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String AddProductPersis(
            @Valid @ModelAttribute("AddProductPersisRequest") AddProductPersisRequest addProductPersisRequest,
            BindingResult bindingResult,
            Model model) {
        log.info("POST /pages/addProducts: maSP={}, size={}, gia={}, soLuong={}, loai={}, gioiTinh={}, files=[main:{}, d1:{}, d2:{}]",
                addProductPersisRequest.getMaSanPham(),
                addProductPersisRequest.getSize(),
                addProductPersisRequest.getGiaSanPham(),
                addProductPersisRequest.getSoLuong(),
                addProductPersisRequest.getLoai(),
                addProductPersisRequest.getGioiTinh(),
                addProductPersisRequest.getAnhChinh() != null ? addProductPersisRequest.getAnhChinh().getOriginalFilename() : "null",
                addProductPersisRequest.getAnhChiTiet1() != null ? addProductPersisRequest.getAnhChiTiet1().getOriginalFilename() : "null",
                addProductPersisRequest.getAnhChiTiet2() != null ? addProductPersisRequest.getAnhChiTiet2().getOriginalFilename() : "null");



        boolean isNew=productsService.findByMaSanPham(addProductPersisRequest.getMaSanPham().trim()).isEmpty();
        if (isNew) {
            if(addProductPersisRequest.getAnhChinh()==null||addProductPersisRequest.getAnhChinh().isEmpty()) {
                bindingResult.rejectValue("anhChinh","anhChinh.required","Định Treo Đầu Dê Bán Thịt Chó À?");
            }
            if(addProductPersisRequest.getAnhChiTiet1()==null||addProductPersisRequest.getAnhChiTiet1().isEmpty()) {
                bindingResult.rejectValue("anhChiTiet1","anhChiTiet1.required","Gán Thêm 1 Ảnh Chi Tiết Đi");
            }
            if(addProductPersisRequest.getAnhChiTiet2()==null||addProductPersisRequest.getAnhChiTiet2().isEmpty()) {
                bindingResult.rejectValue("anhChiTiet2","anhChiTiet2.required","Gán Thêm 1 Ảnh Chi Tiết Đi");
            }
        }


        if (bindingResult.hasErrors()) {
            log.warn("Validation errors when adding product: {}", bindingResult.getAllErrors());
            model.addAttribute("AddProductPersisRequestFailed", true);
            return "pages/addProducts";
        }

        TaiKhoan nguoiCapNhat = (TaiKhoan) httpSession.getAttribute("user");
        if (nguoiCapNhat == null) {
            log.warn("Add product attempted without authenticated user");
            model.addAttribute("AddProductPersisRequestFailed", true);
            model.addAttribute("errorMessage", "Bạn cần đăng nhập trước khi thêm sản phẩm");
            return "pages/addProducts";
        }
        try{
            addProductFlow.addProduct(addProductPersisRequest, nguoiCapNhat);
            model.addAttribute("AddProductPersisRequestSuccess", true);
        }catch(RuntimeException e){
            log.error("Add product failed", e);
            model.addAttribute("AddProductPersisRequestFailed", true);
            return "pages/addProducts";
        }
        return "pages/addProducts";
    }



}
