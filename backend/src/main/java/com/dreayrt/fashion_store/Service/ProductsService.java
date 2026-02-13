package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.AddProductPersisRequest;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;
import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

    public List<SanPham> GetSanPhamList(){
        return sanPhamRepository.findAll();
    }

    public Optional<SanPham> findByMaSanPham(String maSanPham){
        return sanPhamRepository.findById(maSanPham);
    }

    @Transactional
    public SanPham CreateSanPham(@ModelAttribute AddProductPersisRequest req){
//        Optional<SanPham> maSP = sanPhamRepository.findById(req.getMaSanPham());
////        isPresent la co du lieu ben trong hay khong tra ve dang true or false
//        if(maSP.isPresent()){
//            throw new RuntimeException("San Pham Da Ton Tai");
//        }
        SanPham sp = new SanPham();
        sp.setMaSanPham(req.getMaSanPham());
        sp.setTenSanPham(req.getTenSanPham());
        sp.setGiaSanPham(req.getGiaSanPham());
        sp.setLoai(req.getLoai());
        sp.setMoTa(req.getMoTa());
        sp.setGioiTinh(req.getGioiTinh());
        sp.setTag(req.getTag());
//        getOriginalFilename:lay file goc nguoi dung upload len
        String mainName = (req.getAnhChinh() != null && !req.getAnhChinh().isEmpty())
                ? req.getAnhChinh().getOriginalFilename()
                : null;
        String detail1 = (req.getAnhChiTiet1() != null && !req.getAnhChiTiet1().isEmpty())
                ? req.getAnhChiTiet1().getOriginalFilename()
                : null;
        String detail2 = (req.getAnhChiTiet2() != null && !req.getAnhChiTiet2().isEmpty())
                ? req.getAnhChiTiet2().getOriginalFilename()
                : null;
        sp.setAnhChinh(mainName);
        sp.setAnhChiTiet1(detail1);
        sp.setAnhChiTiet2(detail2);
        return sanPhamRepository.save(sp);
    }


    @Transactional
    public SanPhamSize getOrCreateSPSize(SanPham sp,String size){
        return sanPhamSizeRepository.findBySanPham_MaSanPhamAndSize(sp.getMaSanPham(),size)
                .orElseGet(()->{
            SanPhamSize sanPhamSize=new SanPhamSize();
            sanPhamSize.setSanPham(sp);
            sanPhamSize.setSize(size);
            // ensure PK is generated before we use it to create Kho
            return sanPhamSizeRepository.saveAndFlush(sanPhamSize);
        });
    }



    }






