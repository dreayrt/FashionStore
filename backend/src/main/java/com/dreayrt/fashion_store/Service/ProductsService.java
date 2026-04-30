package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.AddProductPersisRequest;
import com.dreayrt.fashion_store.DTOs.UpdateProductRequest;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

    @Autowired
    private R2Service r2Service;

    public List<SanPham> GetSanPhamList() {
        return sanPhamRepository.findAll();
    }

    public Optional<SanPham> findByMaSanPham(String maSanPham) {
        return sanPhamRepository.findById(maSanPham);
    }


    @Transactional
    public SanPham CreateSanPham(@ModelAttribute AddProductPersisRequest req) {
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
        sp.setTrangThai(req.getTrangThai());
        sp.setGioiTinh(req.getGioiTinh());
        sp.setTag(req.getTag());
        String mainName = (req.getAnhChinh() != null && !req.getAnhChinh().isEmpty())
                ? uploadImage(req.getAnhChinh())
                : null;

        String detail1 = (req.getAnhChiTiet1() != null && !req.getAnhChiTiet1().isEmpty())
                ? uploadImage(req.getAnhChiTiet1())
                : null;

        String detail2 = (req.getAnhChiTiet2() != null && !req.getAnhChiTiet2().isEmpty())
                ? uploadImage(req.getAnhChiTiet2())
                : null;
        sp.setAnhChinh(mainName);
        sp.setAnhChiTiet1(detail1);
        sp.setAnhChiTiet2(detail2);
        return sanPhamRepository.save(sp);
    }

    public boolean isDuplicateTenSanPhamForCreate(String tenSanPham) {
        return sanPhamRepository.existsByTenSanPham(tenSanPham.trim());
    }


    @Transactional
    public SanPhamSize getOrCreateSPSize(SanPham sp, String size) {
        return sanPhamSizeRepository.findBySanPham_MaSanPhamAndSize(sp.getMaSanPham(), size)
                .orElseGet(() -> {
                    SanPhamSize sanPhamSize = new SanPhamSize();
                    sanPhamSize.setSanPham(sp);
                    sanPhamSize.setSize(size);
                    // ensure PK is generated before we use it to create Kho
                    return sanPhamSizeRepository.saveAndFlush(sanPhamSize);
                });
    }

    @Transactional
    public void updateProduct(UpdateProductRequest req) {
        SanPham sp = sanPhamRepository.findById(req.getMaSanPham()).orElseThrow(() -> new RuntimeException("Khong Tim Thay Ma San Pham"));
        sp.setTenSanPham(req.getTenSanPham());
        sp.setGiaSanPham(req.getGiaSanPham());
        sp.setLoai(req.getLoai());
        sp.setMoTa(req.getMoTa());
        sp.setGioiTinh(req.getGioiTinh());
        sp.setTag(req.getTag());
        sp.setTrangThai(req.getTrangThai());

        if (req.getAnhChinh() != null && !req.getAnhChinh().isEmpty()) {
            String fileName = uploadImage(req.getAnhChinh());
            sp.setAnhChinh(fileName);
        } else {
            sp.setAnhChinh(req.getOldAnhChinh());
        }
        if (req.getAnhChiTiet1() != null && !req.getAnhChiTiet1().isEmpty()) {
            String fileName = uploadImage(req.getAnhChiTiet1());
            sp.setAnhChiTiet1(fileName);
        } else {
            sp.setAnhChiTiet1(req.getOldAnhChiTiet1());
        }
        if (req.getAnhChiTiet2() != null && !req.getAnhChiTiet2().isEmpty()) {
            String fileName = uploadImage(req.getAnhChiTiet2());
            sp.setAnhChiTiet2(fileName);
        } else {
            sp.setAnhChiTiet2(req.getOldAnhChiTiet2());
        }
        sanPhamRepository.save(sp);


        SanPhamSize size = sanPhamSizeRepository.findBySanPham_MaSanPhamAndSize(req.getMaSanPham(), req.getSize()).orElseThrow(() -> new RuntimeException("Khong Tim Thay Size San Pham"));

        if (size.getKho() != null) {
            size.getKho().setSoLuong(req.getSoLuong());
        }
        sanPhamSizeRepository.save(size);


    }

    public boolean isDuplicateTenSanPhamForUpdate(String tenSanPham, String maSanPham) {
        return sanPhamRepository.existsByTenSanPhamAndMaSanPhamNot(
                tenSanPham.trim(),
                maSanPham.trim()
        );
    }


    @Transactional
    public void deleteSanPham(String masp, String size) {
        SanPhamSize spSize = sanPhamSizeRepository.findBySanPham_MaSanPhamAndSize(masp, size).orElseThrow(() -> new RuntimeException("Không Tìm Thấy Sản Phẩm"));
        sanPhamSizeRepository.delete(spSize);
    }

    private String uploadImage(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String key = "imageProduct/" + fileName;

            r2Service.uploadFile(key, file);

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Upload R2 lỗi", e);
        }
    }
}













