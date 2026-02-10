package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Kho")
public class Kho {
    @Id
    @Column(name = "MaSPSize")
    private Integer maSPSize;

    @OneToOne
    @MapsId
    @JoinColumn(name = "MaSPSize")
    private SanPhamSize sanPhamSize;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NguoiCapNhat")
    private TaiKhoan nguoiCapNhat;

    public Integer getMaSPSize() {
        return maSPSize;
    }



    public SanPhamSize getSanPhamSize() {
        return sanPhamSize;
    }

    public void setSanPhamSize(SanPhamSize sanPhamSize) {
        this.sanPhamSize = sanPhamSize;
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
}
