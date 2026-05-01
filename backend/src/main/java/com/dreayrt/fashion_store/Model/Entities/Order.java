package com.dreayrt.fashion_store.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "DonHang")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDonHang")
    private Integer maDonHang;

    @Column(name = "NgayDat")
    private Date ngayDat;
    @Column(name = "TongTien")
    private BigDecimal tongTien;
    @Column(name = "PhuongThucThanhToan")
    private String phuongThucThanhToan;
    @Column(name = "TrangThai")
    private String trangThai;
    @Column(name = "TenNguoiNhan")
    private String tenNguoiNhan;
    @Column(name = "SoDienThoai")
    private String soDienThoai;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "DaThanhToan")
    private Boolean daThanhToan;

    @Column(name = "NgayGiaoHang")
    private Date ngayGiaoHang;

    @Column(name = "ThoiGianGiaoDuKien")
    private Integer thoiGianGiaoDuKien; // Đơn vị: giây

    @Column(name = "NgayHoanThanhDuKien")
    private Date ngayHoanThanhDuKien;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetail;


    public Integer getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(Integer maDonHang) {
        this.maDonHang = maDonHang;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Boolean getDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(Boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    public Date getNgayGiaoHang() {
        return ngayGiaoHang;
    }

    public void setNgayGiaoHang(Date ngayGiaoHang) {
        this.ngayGiaoHang = ngayGiaoHang;
    }

    public Integer getThoiGianGiaoDuKien() {
        return thoiGianGiaoDuKien;
    }

    public void setThoiGianGiaoDuKien(Integer thoiGianGiaoDuKien) {
        this.thoiGianGiaoDuKien = thoiGianGiaoDuKien;
    }

    public Date getNgayHoanThanhDuKien() {
        return ngayHoanThanhDuKien;
    }

    public void setNgayHoanThanhDuKien(Date ngayHoanThanhDuKien) {
        this.ngayHoanThanhDuKien = ngayHoanThanhDuKien;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }


    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
