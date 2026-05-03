package com.dreayrt.fashion_store.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Voucher")
public class Voucher {
    @Id
    @Column(name = "MaVoucher")
    private String maVoucher;
    @Column(name = "GiaTri")
    private BigDecimal giaTri;
    @Column (name = "Loai")
    private String loai;
    @Column(name = "SoLuong")
    private Integer soLuong;
    @Column(name = "NgayHetHan")
    private LocalDateTime ngayHetHan;
    @Column (name = "GiaTriToiThieu")
    private BigDecimal giaTriToiThieu;

    @OneToMany(mappedBy = "voucher")
    private List<Order> orders;

    @OneToOne(mappedBy = "voucher" )
    @JsonIgnore
    private Advertisement advertisement;

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public LocalDateTime getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDateTime ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public BigDecimal getGiaTriToiThieu() {
        return giaTriToiThieu;
    }

    public void setGiaTriToiThieu(BigDecimal giaTriToiThieu) {
        this.giaTriToiThieu = giaTriToiThieu;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
