package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.AdvertisementDTO;
import com.dreayrt.fashion_store.Model.Entities.Advertisement;
import com.dreayrt.fashion_store.Model.Entities.Voucher;
import com.dreayrt.fashion_store.repository.AdvertisementRepository;
import com.dreayrt.fashion_store.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdvertisementService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Advertisement createAdvertisement(AdvertisementDTO dto) {

        Voucher voucher = new Voucher();
        voucher.setMaVoucher(dto.getMaVoucher());
        voucher.setGiaTriToiThieu(dto.getGiaTriToiThieu());
        voucher.setGiaTri(dto.getGiaTriGiam());
        voucher.setLoai(dto.getLoaiVoucher());
        voucher.setSoLuong(dto.getSoLuong());
        voucher.setNgayHetHan(dto.getNgayHetHan());
        voucherRepository.save(voucher);


        Advertisement newAdvertisement = new Advertisement();
        newAdvertisement.setNoiDung(dto.getNoiDungQC());
        newAdvertisement.setVoucher(voucher);
        newAdvertisement.setNgayBatDau(LocalDateTime.now());
        newAdvertisement.setNgayKetThuc(voucher.getNgayHetHan());
        return advertisementRepository.save(newAdvertisement);

    }
    public List<Advertisement> getActiveAds() {
        return advertisementRepository.findActiveAds();
    }

}
