package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.AddProductPersisRequest;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddProductFlow {
    @Autowired
    private ProductsService productsService;
    @Autowired
    private PersistenceService persistenceService;

    @Transactional
    public void addProduct(AddProductPersisRequest req, TaiKhoan user){
        SanPham sp = productsService.saveOrUpdateSanPham(req);
        SanPhamSize size=productsService.getOrCreateSPSize(sp,req.getSize());
        persistenceService.upSetPer(size,req.getSoLuong(),user);


    }
}
