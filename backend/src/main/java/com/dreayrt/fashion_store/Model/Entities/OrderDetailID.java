package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDetailID implements Serializable {
    @Column(name = "MaDonHang")
    private Integer maDonHang;
    @Column(name = "MaSPSize")
    private Integer maSanPhamSize;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof OrderDetailID)) return false;
        OrderDetailID that = (OrderDetailID) o;
        return Objects.equals(maDonHang,that.maDonHang)&&Objects.equals(maSanPhamSize,that.maSanPhamSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDonHang,maSanPhamSize);
    }

    public Integer getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(Integer maDonHang) {
        this.maDonHang = maDonHang;
    }

    public Integer getMaSanPhamSize() {
        return maSanPhamSize;
    }

    public void setMaSanPhamSize(Integer maSanPhamSize) {
        this.maSanPhamSize = maSanPhamSize;
    }
}
