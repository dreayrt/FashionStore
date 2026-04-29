package com.dreayrt.fashion_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderRequest {
    @NotBlank(message = "Rồi Ai Nhận Đây?")
    @Size(max = 100, message = "Tên Dài Dữ mạy")
    private String recipientName;
    @NotBlank(message = "Không Điền Số Điện Thoại Này")
    @Pattern(regexp = "^[0-9]{10}$", message = "SĐT Chưa Đúng Nè")
    private String recipientPhone;
    @NotBlank(message = "Giao Đại Hay Sao Mà Không Điền Địa Chỉ")
    @Size(max = 255, message = "Địa Chỉ Quá Dài")
    private String recipientAddress;
    @NotBlank()
    @Pattern(regexp = "COD|SEPAY|BANK", message = "Phương Thức Không Hợp Lệ")
    private String paymentMethod;

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
