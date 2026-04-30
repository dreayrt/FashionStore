package com.dreayrt.fashion_store.DTOs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SanPhamKhoGroupDTO {
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private BigDecimal giaSanPham;
    private String trangThai;
    private String anhChinh;
    private Integer tongTonKho;
    private List<SizeDetail> chiTietTheoSize;

    public static class SizeDetail {
        private String size;
        private Integer soLuong;
        private Date ngayCapNhat;
        private String nguoiCapNhat;

        public SizeDetail() {}

        public SizeDetail(String size, Integer soLuong, Date ngayCapNhat, String nguoiCapNhat) {
            this.size = size;
            this.soLuong = soLuong;
            this.ngayCapNhat = ngayCapNhat;
            this.nguoiCapNhat = nguoiCapNhat;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
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

        public String getNguoiCapNhat() {
            return nguoiCapNhat;
        }

        public void setNguoiCapNhat(String nguoiCapNhat) {
            this.nguoiCapNhat = nguoiCapNhat;
        }
    }

    public SanPhamKhoGroupDTO() {}

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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public BigDecimal getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(BigDecimal giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getAnhChinh() {
        return anhChinh;
    }

    public void setAnhChinh(String anhChinh) {
        this.anhChinh = anhChinh;
    }

    public Integer getTongTonKho() {
        return tongTonKho;
    }

    public void setTongTonKho(Integer tongTonKho) {
        this.tongTonKho = tongTonKho;
    }

    public List<SizeDetail> getChiTietTheoSize() {
        return chiTietTheoSize;
    }

    public void setChiTietTheoSize(List<SizeDetail> chiTietTheoSize) {
        this.chiTietTheoSize = chiTietTheoSize;
    }
}
