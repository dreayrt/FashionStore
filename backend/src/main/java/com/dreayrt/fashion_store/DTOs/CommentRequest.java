package com.dreayrt.fashion_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CommentRequest {

    @NotBlank(message = "Bạn Hãy Comment Trước Khi CLick Gửi Chứ")
    private String noiDung;
    @NotNull(message = "Bạn Quên Ratting Cho Sản Phẩm Nè")
    private Integer rating;
    @NotBlank(message = "Thiếu mã sản phẩm")
    private String maSanPham;
    private Date ngayBinhLuan;

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public Date getNgayBinhLuan() {
        return ngayBinhLuan;
    }

    public void setNgayBinhLuan(Date ngayBinhLuan) {
        this.ngayBinhLuan = ngayBinhLuan;
    }
}
