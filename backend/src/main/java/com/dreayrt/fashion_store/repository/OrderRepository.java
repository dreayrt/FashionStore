package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
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

    boolean existsByTaiKhoan_UsernameAndTrangThaiAndOrderDetail_SanPhamSize_SanPham_MaSanPham(String username, String trangThai, String maSanPham);

    @Query("SELECT SUM(o.tongTien) FROM Order o WHERE o.trangThai = 'Hoàn thành'")
    BigDecimal sumTotalRevenue();
}
