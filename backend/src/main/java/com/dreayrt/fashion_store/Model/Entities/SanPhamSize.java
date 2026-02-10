package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "SanPhamSize")
public class SanPhamSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maSPSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSanPham")
    private SanPham sanPham;

    @Column(name = "Size")
    private String size;

    @OneToOne(mappedBy = "sanPhamSize", cascade = CascadeType.ALL)
    private Kho kho;

    public Integer getMaSPSize() {
        return maSPSize;
    }

    public void setMaSPSize(Integer maSPSize) {
        this.maSPSize = maSPSize;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Kho getKho() {
        return kho;
    }

    public void setKho(Kho kho) {
        this.kho = kho;
    }
}
