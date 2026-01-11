package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    public List<SanPham> GetSanPhamList(){
        return sanPhamRepository.findAll();
    }


}
