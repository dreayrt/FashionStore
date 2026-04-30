package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.AddProductPersisRequest;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddProductFlow {
    @Autowired
    private ProductsService productsService;
    @Autowired
    private PersistenceService persistenceService;

    @Value("${app.r2.public-url}")
    private String publicUrl;

    @Autowired
    private R2Service r2Service;

    @Transactional
    public void addProduct(AddProductPersisRequest req, TaiKhoan user){
        try {
            String fileNameMain = UUID.randomUUID() + "_" + req.getAnhChinh().getOriginalFilename();
            String fileNameDetail1 = UUID.randomUUID() + "_" + req.getAnhChiTiet1().getOriginalFilename();
            String fileNameDetail2 = UUID.randomUUID() + "_" + req.getAnhChiTiet2().getOriginalFilename();

            r2Service.uploadFile(fileNameMain, req.getAnhChinh());
            r2Service.uploadFile(fileNameDetail1, req.getAnhChiTiet1());
            r2Service.uploadFile(fileNameDetail2, req.getAnhChiTiet2());

            req.setAnhChinhName(fileNameMain);
            req.setAnhChiTiet1Name(fileNameDetail1);
            req.setAnhChiTiet2Name(fileNameDetail2);

            SanPham sp = productsService.CreateSanPham(req);

            SanPhamSize size = productsService.getOrCreateSPSize(sp, req.getSize());
            persistenceService.upSetPer(size, req.getSoLuong(), user);

        } catch (Exception e) {
            throw new RuntimeException("Upload failed", e);
        }
    }
}
