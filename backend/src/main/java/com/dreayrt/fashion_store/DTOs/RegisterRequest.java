package com.dreayrt.fashion_store.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Không Bỏ Trống Tên Đăng Nhập")
    private String username;
    @NotBlank(message = "Không Bỏ Trống Mật Khẩu")
    @Size(min = 6 ,message="Mật Khẩu Phải Có Độ Dài Lớn Hơn 6 Ký Tự")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "Mật Khẩu Phải Chứa Chữ In Hoa, Số Và Ký Tự Đặc Biệt"
    )
    private String password;
    @NotBlank(message = "Không Bỏ Trống Xác Nhận Mật Khẩu")
    private String confirmPassword;
    @NotBlank(message = "Không Bỏ Trống Địa Chỉ Email")
    @Email(message="Email Chưa Đúng Định Dạng")
    private String email;
    @NotBlank(message = "Không Bỏ Trống Số Điện Thoại")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Số điện thoại phải đúng 10 chữ số"
    )
    private String phone;
    @NotBlank(message = "Không Bỏ Trống Địa Chỉ Nhà")
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
