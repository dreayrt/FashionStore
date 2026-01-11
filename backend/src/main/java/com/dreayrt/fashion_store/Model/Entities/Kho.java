package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Kho")
public class Kho {
    @Id
    @Column(name = "MaSanPham")
    private String maSanPham;

    @Column(name = "SoLuong")
    private Integer soLuong;
    @Column(name = "NgayCapNhat")
    private Date ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="NguoiCapNhat")
    private TaiKhoan nguoiCapNhat;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="MaSanPham")
    private SanPham sanPham;

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(Date ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public TaiKhoan getNguoiCapNhat() {
        return nguoiCapNhat;
    }

    public void setNguoiCapNhat(TaiKhoan nguoiCapNhat) {
        this.nguoiCapNhat = nguoiCapNhat;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
