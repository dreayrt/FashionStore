package com.dreayrt.fashion_store.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OtpForgotRequest {
    @NotBlank(message = "Không Được Để Trống Email")
    private String email;
    @NotBlank(message = "Không Được Để Trống OTP")
    private String otp;
    @Size(min=6,message="Mật Khẩu Tối Thiểu 6 Ký Tự")
    @NotBlank(message = "Không Được Để Trống Mật Khẩu")
    private String password;
    @NotBlank(message = "Không Được Để Trống Xác Nhận Mật Khẩu ")
    private String ComfirmPassword;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getComfirmPassword() {
        return ComfirmPassword;
    }

    public void setComfirmPassword(String comfirmPassword) {
        ComfirmPassword = comfirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
