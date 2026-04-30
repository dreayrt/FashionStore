package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.SanPhamKhoGroupDTO;
import com.dreayrt.fashion_store.Model.Entities.Kho;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.PersistenceRepository;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersistenceService {
    @Autowired
    PersistenceRepository persistenceRepository;
    @Autowired
    SanPhamSizeRepository sanPhamSizeRepository;

    public List<Kho> getSanPhamKhoList(){
        return persistenceRepository.findAll();
    }
    @Transactional
    public Kho upSetPer(SanPhamSize sanPhamSize, Integer soLuong,TaiKhoan nguoiCapNhat){
        Date date = new Date();
        // defensive: make sure SanPhamSize has a generated PK before using it as Kho's @MapsId
        if (sanPhamSize.getMaSPSize() == null) {
            sanPhamSize = sanPhamSizeRepository.saveAndFlush(sanPhamSize);
        }

        final SanPhamSize finalSanPhamSize = sanPhamSize;
        final Integer maSPSize = finalSanPhamSize.getMaSPSize();
        if (maSPSize == null) {
            throw new IllegalStateException("SanPhamSize id is still null after save");
        }
        // ensure we are working with a managed SanPhamSize instance

        return persistenceRepository.findById(maSPSize).map(kho ->{
            kho.setNguoiCapNhat(nguoiCapNhat);
            kho.setSoLuong(kho.getSoLuong()+soLuong);
            kho.setNgayCapNhat(date);
            return kho;
        }).orElseGet(()->
                {
                    Kho kho =new Kho();
                    kho.setSanPhamSize(finalSanPhamSize);
                    kho.setNguoiCapNhat(nguoiCapNhat);
                    kho.setSoLuong(soLuong);
                    kho.setNgayCapNhat(date);
                    return persistenceRepository.save(kho);
                }
        );
    }

    public List<SanPhamKhoGroupDTO> getSanPhamKhoGroupedList() {
        List<Kho> khoList = persistenceRepository.findAll();

        // Group theo maSanPham
        Map<String, List<Kho>> groupedByMaSP = khoList.stream()
                .collect(Collectors.groupingBy(k -> k.getSanPhamSize().getSanPham().getMaSanPham()));

        List<SanPhamKhoGroupDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<Kho>> entry : groupedByMaSP.entrySet()) {
            String maSanPham = entry.getKey();
            List<Kho> spKhoList = entry.getValue();

            // Lay SanPham tu item dau tien (tat ca cung 1 SanPham)
            SanPham sanPham = spKhoList.get(0).getSanPhamSize().getSanPham();

            SanPhamKhoGroupDTO dto = new SanPhamKhoGroupDTO();
            dto.setMaSanPham(sanPham.getMaSanPham());
            dto.setTenSanPham(sanPham.getTenSanPham());
            dto.setMoTa(sanPham.getMoTa());
            dto.setGiaSanPham(sanPham.getGiaSanPham());
            dto.setTrangThai(sanPham.getTrangThai());
            dto.setAnhChinh(sanPham.getAnhChinh());

            // Tinh tong ton kho
            int tongTonKho = spKhoList.stream()
                    .mapToInt(Kho::getSoLuong)
                    .sum();
            dto.setTongTonKho(tongTonKho);

            // Tao danh sach chi tiet theo size
            List<SanPhamKhoGroupDTO.SizeDetail> chiTietList = spKhoList.stream()
                    .sorted(Comparator.comparing(k -> k.getSanPhamSize().getSize()))
                    .map(kho -> {
                        SanPhamKhoGroupDTO.SizeDetail detail = new SanPhamKhoGroupDTO.SizeDetail();
                        detail.setSize(kho.getSanPhamSize().getSize());
                        detail.setSoLuong(kho.getSoLuong());
                        detail.setNgayCapNhat(kho.getNgayCapNhat());
                        TaiKhoan nguoiCapNhat = kho.getNguoiCapNhat();
                        detail.setNguoiCapNhat(nguoiCapNhat != null ? nguoiCapNhat.getUsername() : "-");
                        return detail;
                    })
                    .collect(Collectors.toList());
            dto.setChiTietTheoSize(chiTietList);

            result.add(dto);
        }

        // Sort theo maSanPham tang dan
        result.sort(Comparator.comparing(SanPhamKhoGroupDTO::getMaSanPham));

        return result;
    }
}
