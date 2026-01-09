package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "OtpLog")
public class OtpLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OtpId")
    private Integer otpid;
    @Column(name = "Email")
    private String email;
    @Column(name = "OtpHash")
    private String otp;
    @Column(name = "CreateAt")
    private Date createat;
    @Column(name = "ExpiredAt")
    private Date expiratedat;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserName",nullable = false)
    private TaiKhoan taiKhoan;


    public Integer getOtpid() {
        return otpid;
    }

    public void setOtpid(Integer otpid) {
        this.otpid = otpid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    public Date getExpiratedat() {
        return expiratedat;
    }

    public void setExpiratedat(Date expiratedat) {
        this.expiratedat = expiratedat;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
}
