package com.dreayrt.fashion_store.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public class AdvertisementDTO {
    @NotBlank(message = "Nội dung quảng cáo không được để trống")
    private String noiDungQC;

    @NotBlank(message = "Mã voucher không được để trống")
    private String maVoucher;

    @NotBlank(message = "Loại voucher không được để trống")
    private String loaiVoucher;

    @NotNull(message = "Giá trị giảm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị giảm phải lớn hơn 0")
    private BigDecimal giaTriGiam;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải ít nhất là 1")
    private Integer soLuong;

    @NotNull(message = "Ngày hết hạn không được để trống")
    @Future(message = "Ngày hết hạn phải ở tương lai")
    private LocalDateTime ngayHetHan;

    @NotNull(message = "Giá trị tối thiểu không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá trị tối thiểu không được âm")
    private BigDecimal giaTriToiThieu;

    public String getNoiDungQC() {
        return noiDungQC;
    }

    public void setNoiDungQC(String noiDungQC) {
        this.noiDungQC = noiDungQC;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getLoaiVoucher() {
        return loaiVoucher;
    }

    public void setLoaiVoucher(String loaiVoucher) {
        this.loaiVoucher = loaiVoucher;
    }

    public BigDecimal getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(BigDecimal giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public LocalDateTime getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDateTime ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public BigDecimal getGiaTriToiThieu() {
        return giaTriToiThieu;
    }

    public void setGiaTriToiThieu(BigDecimal giaTriToiThieu) {
        this.giaTriToiThieu = giaTriToiThieu;
    }
}
