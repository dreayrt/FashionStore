package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Service.ProductsService;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String removeProductPersis(Model model) {
        List<SanPhamSize> lstSize = sanPhamSizeRepository.findAll();
        model.addAttribute("lstSize", lstSize);
        return "pages/deleteProducts";
    }

    @PostMapping("/pages/deleteProducts")
    public String deleteProductPersis(@RequestParam String maSanPham, @RequestParam String size,
                                      RedirectAttributes redirectAttributes) {
        try {
            productsService.deleteSanPham(maSanPham, size);
            redirectAttributes.addFlashAttribute("DeleteSuccess", true);
        } catch (DataIntegrityViolationException e)
//        DataIntegrityViolationException thường do ràng buộc DB (foreign key, v.v.)
        {
            redirectAttributes.addFlashAttribute("DeleteError", true);
            redirectAttributes.addFlashAttribute("DeleteErrorMessage", "Sản Phẩm Đang Được Trưng Bày, Không Thể Xóa");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("DeleteError", true);
            String msg = (e.getMessage() != null && e.getMessage().contains("Không Tìm Thấy"))
                    ? "Không tìm thấy sản phẩm."
                    : "Xóa không thành công. Vui lòng thử lại.";
            redirectAttributes.addFlashAttribute("DeleteErrorMessage", msg);
        }
        return "redirect:/pages/deleteProducts";
    }
}
