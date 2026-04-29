package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    long countByTrangThai(String trangThai);
    Order findByMaDonHangAndTaiKhoan_Username(Integer maDonHang,String userName);
    @EntityGraph(attributePaths = {
            "orderDetail",
            "orderDetail.sanPhamSize",
            "orderDetail.sanPhamSize.displayItem"
    })
    List<Order> findAllByTaiKhoan_Username(String userName);

    @EntityGraph(attributePaths = {
            "orderDetail",
            "orderDetail.sanPhamSize",
            "orderDetail.sanPhamSize.sanPham",
            "orderDetail.sanPhamSize.kho"
    })
    @Override
    List<Order> findAll();
}
