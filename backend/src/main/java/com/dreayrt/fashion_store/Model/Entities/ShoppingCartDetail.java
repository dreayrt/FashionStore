package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "GioHangChiTiet")
public class ShoppingCartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "SoLuong")
    private Integer soLuong;
    @ManyToOne()
    @JoinColumn(name ="maGioHang")
    private ShoppingCart shoppingCart;
    @ManyToOne()
    @JoinColumn(name = "MaSPSize")
    private SanPhamSize sanPhamSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public SanPhamSize getSanPhamSize() {
        return sanPhamSize;
    }

    public void setSanPhamSize(SanPhamSize sanPhamSize) {
        this.sanPhamSize = sanPhamSize;
    }
}
