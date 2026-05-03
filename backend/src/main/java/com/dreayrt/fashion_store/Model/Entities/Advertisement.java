package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "QuangCao")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaQuangCao")
    private Integer maQuangCao;
    @Column(name = "NoiDung")
    private String noiDung;
    @Column(name = "NgayBatDau")
    private LocalDateTime ngayBatDau;
    @Column(name = "NgayKetThuc")
    private LocalDateTime ngayKetThuc;

    @OneToOne()
    @JoinColumn(name = "MaVoucher")
    private Voucher voucher;

    public Integer getMaQuangCao() {
        return maQuangCao;
    }

    public void setMaQuangCao(Integer maQuangCao) {
        this.maQuangCao = maQuangCao;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}
