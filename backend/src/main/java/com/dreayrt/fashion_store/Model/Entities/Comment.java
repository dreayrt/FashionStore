package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "BinhLuan")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBinhLuan")
    private Integer MaBinhLuan;
    @Column(name = "NoiDung")
    private String NoiDung;
    @Column(name = "NgayBinhLuan")
    private Date NgayBinhLuan;
    @Column(name ="DanhGia" )
    private int DanhGia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserName")
    private TaiKhoan user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSanPham")
    private SanPham sanPham;


    public Integer getMaBinhLuan() {
        return MaBinhLuan;
    }

    public void setMaBinhLuan(Integer maBinhLuan) {
        MaBinhLuan = maBinhLuan;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public Date getNgayBinhLuan() {
        return NgayBinhLuan;
    }

    public void setNgayBinhLuan(Date ngayBinhLuan) {
        NgayBinhLuan = ngayBinhLuan;
    }

    public int getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(int danhGia) {
        DanhGia = danhGia;
    }

    public TaiKhoan getUser() {
        return user;
    }

    public void setUser(TaiKhoan user) {
        this.user = user;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
