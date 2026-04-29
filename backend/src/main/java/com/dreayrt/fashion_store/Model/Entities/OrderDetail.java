package com.dreayrt.fashion_store.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "DonHangChiTiet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDetail {
    @EmbeddedId
    private OrderDetailID orderDetailID;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name ="DonGia" )
    private BigDecimal donGia;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maDonHang")
    @JoinColumn(name = "MaDonHang")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
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


