package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "HangTrungBay")
public class DisplayItem {
    @Id
    @Column(name = "MaSPSize")
    private Integer maSPSize;
    @Column(name = "SoLuong")
    private int soLuong;
    @Column(name = "GiaBan")
    private BigDecimal giaBan;
    @Column(name = "NgayTrungBay")
    private Date ngayTrungBay;
    @OneToOne
    @MapsId
    @JoinColumn(name = "MaSPSize")
    private SanPhamSize sanPhamSize;

    public Integer getMaSPSize() {
        return maSPSize;
    }

    public void setMaSPSize(Integer maSPSize) {
        this.maSPSize = maSPSize;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public Date getNgayTrungBay() {
        return ngayTrungBay;
    }

    public void setNgayTrungBay(Date ngayTrungBay) {
        this.ngayTrungBay = ngayTrungBay;
    }

    public SanPhamSize getSanPhamSize() {
        return sanPhamSize;
    }

    public void setSanPhamSize(SanPhamSize sanPhamSize) {
        this.sanPhamSize = sanPhamSize;
    }
}
