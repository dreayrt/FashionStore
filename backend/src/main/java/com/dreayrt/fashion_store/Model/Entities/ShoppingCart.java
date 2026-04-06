package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "GioHang")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGioHang")
    private Integer maGioHang;
    @OneToOne
    @JoinColumn(name ="UserName")
    private TaiKhoan taikhoan;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartDetail> items;

    public Integer getId() {
        return maGioHang;
    }

    public void setId(Integer id) {
        this.maGioHang = id;
    }

    public TaiKhoan getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(TaiKhoan taikhoan) {
        this.taikhoan = taikhoan;
    }

    public Integer getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(Integer maGioHang) {
        this.maGioHang = maGioHang;
    }

    public List<ShoppingCartDetail> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartDetail> items) {
        this.items = items;
    }
}
