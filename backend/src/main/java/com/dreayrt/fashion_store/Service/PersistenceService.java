package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.Model.Entities.Kho;
import com.dreayrt.fashion_store.Model.Entities.SanPhamSize;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.PersistenceRepository;
import com.dreayrt.fashion_store.repository.SanPhamSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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




}
