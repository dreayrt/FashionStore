package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "SanPham")
public class SanPham {
    @Id
    @Column(name="MaSanPham")
    private String maSanPham;

    @Column(name="TenSanPham")
    private String tenSanPham;

    @Column(name="GiaSanPham")
    private BigDecimal giaSanPham;

    @Column(name="Loai")
    private String loai;

    @Column(name="TrangThai")
    private String trangThai;

    @Column(name="MoTa")
    private String moTa;
    @Column(name ="Tag")
    private String tag;
    @Column(name = "GioiTinh")
    private Integer gioiTinh;
    @Column(name="AnhChinh")
    private String anhChinh;
    @Column(name="AnhChiTiet1")
    private String anhChiTiet1;
    @Column(name="AnhChiTiet2")
    private String anhChiTiet2;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    private List<SanPhamSize> sizes;



    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public BigDecimal getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(BigDecimal giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAnhChinh() {
        return anhChinh;
    }

    public void setAnhChinh(String anhChinh) {
        this.anhChinh = anhChinh;
    }

    public String getAnhChiTiet2() {
        return anhChiTiet2;
    }

    public void setAnhChiTiet2(String anhChiTiet2) {
        this.anhChiTiet2 = anhChiTiet2;
    }

    public String getAnhChiTiet1() {
        return anhChiTiet1;
    }

    public void setAnhChiTiet1(String anhChiTiet1) {
        this.anhChiTiet1 = anhChiTiet1;
    }
}
