package com.dreayrt.fashion_store.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class UpdateProductRequest {
    @NotBlank(message = "Không Để Trống Mã Sản Phẩm")
    private String maSanPham;
    @NotBlank(message = "Không Để Trống Tên Sản Phẩm")
    private String tenSanPham;
    @NotBlank(message = "Bạn Quên Đặt Lại Size Nè")
    private String size;
    @NotNull(message = "Bạn Quên Đặt Lại Giá Nè")
    @Min(value = 0,message = "Định Cho Ngược Lại Người Mua À")
    private BigDecimal giaSanPham;
    @NotNull(message = "Bạn Chưa Đặt Số Lượng Nhập Vào Kho")
    @Min(value = 0,message = "Số Lượng Nhập Mà Nhỏ Hơn 0?")
    private Integer soLuong;
    @NotBlank(message = "Xóa Mất Tag Người Ta Rồi Thêm Lại Đi")
    private String tag;
    @NotBlank(message = "Bạn Quên Đặt Loại Cho Sản Phẩm Nè")
    private String loai;
    @NotNull(message = "Sản Phẩm Này Cho Phái Nào Mặc?")
    private Integer gioiTinh;
    @NotBlank(message = "Phải Mô Tả Sản Phẩm 1 Xíu Chứ")
    private String moTa;
    private MultipartFile anhChinh;
    private MultipartFile anhChiTiet1;
    private MultipartFile anhChiTiet2;
    private String oldAnhChinh;
    private String oldAnhChiTiet1;
    private String oldAnhChiTiet2;


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

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public MultipartFile getAnhChinh() {
        return anhChinh;
    }

    public void setAnhChinh(MultipartFile anhChinh) {
        this.anhChinh = anhChinh;
    }

    public MultipartFile getAnhChiTiet1() {
        return anhChiTiet1;
    }

    public void setAnhChiTiet1(MultipartFile anhChiTiet1) {
        this.anhChiTiet1 = anhChiTiet1;
    }

    public MultipartFile getAnhChiTiet2() {
        return anhChiTiet2;
    }

    public void setAnhChiTiet2(MultipartFile anhChiTiet2) {
        this.anhChiTiet2 = anhChiTiet2;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getOldAnhChinh() {
        return oldAnhChinh;
    }

    public void setOldAnhChinh(String oldAnhChinh) {
        this.oldAnhChinh = oldAnhChinh;
    }

    public String getOldAnhChiTiet1() {
        return oldAnhChiTiet1;
    }

    public void setOldAnhChiTiet1(String oldAnhChiTiet1) {
        this.oldAnhChiTiet1 = oldAnhChiTiet1;
    }

    public String getOldAnhChiTiet2() {
        return oldAnhChiTiet2;
    }

    public void setOldAnhChiTiet2(String oldAnhChiTiet2) {
        this.oldAnhChiTiet2 = oldAnhChiTiet2;
    }
}
