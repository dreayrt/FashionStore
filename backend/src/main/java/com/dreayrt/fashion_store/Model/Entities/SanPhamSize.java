package com.dreayrt.fashion_store.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SanPhamSize")
public class SanPhamSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maSPSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSanPham")
    @JsonIgnore
    private SanPham sanPham;

    @Column(name = "Size")
    private String size;

    @OneToOne(mappedBy = "sanPhamSize", cascade = CascadeType.ALL)
    private Kho kho;

    @OneToMany(mappedBy = "sanPhamSize")
    private List<ShoppingCartDetail> shoppingCartDetailList;

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

    public List<ShoppingCartDetail> getShoppingCartDetailList() {
        return shoppingCartDetailList;
    }

    public void setShoppingCartDetailList(List<ShoppingCartDetail> shoppingCartDetailList) {
        this.shoppingCartDetailList = shoppingCartDetailList;
    }
}
