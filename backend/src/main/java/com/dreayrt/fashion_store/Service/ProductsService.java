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

        if (req.getAnhChinh() != null && !req.getAnhChinh().isEmpty()) {
            String fileName = saveFile(req.getAnhChinh());
            sp.setAnhChinh(fileName);
        } else {
            sp.setAnhChinh(req.getOldAnhChinh());
        }
        if (req.getAnhChiTiet1() != null && !req.getAnhChiTiet1().isEmpty()) {
            String fileName = saveFile(req.getAnhChiTiet1());
            sp.setAnhChiTiet1(fileName);
        } else {
            sp.setAnhChiTiet1(req.getOldAnhChiTiet1());
        }
        if (req.getAnhChiTiet2() != null && !req.getAnhChiTiet2().isEmpty()) {
            String fileName = saveFile(req.getAnhChiTiet2());
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
    public String saveFile(MultipartFile file) {
        try {
            String uploadDir = "/frontend/public/imageProduct/";
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), path);
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi lưu file", e);
        }
    }

}













