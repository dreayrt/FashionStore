package com.dreayrt.fashion_store.Model.Entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "DonHangChiTiet")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailID orderDetailID;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name ="DonGia" )
    private BigDecimal donGia;


    @ManyToOne
    @MapsId("maDonHang")
    @JoinColumn(name = "MaDonHang")
    private Order order;

    @ManyToOne
    @MapsId("maSanPhamSize")
    @JoinColumn(name = "MaSPSize")
    private SanPhamSize sanPhamSize;

    public OrderDetailID getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(OrderDetailID orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public SanPhamSize getSanPhamSize() {
        return sanPhamSize;
    }

    public void setSanPhamSize(SanPhamSize sanPhamSize) {
        this.sanPhamSize = sanPhamSize;
    }
}


