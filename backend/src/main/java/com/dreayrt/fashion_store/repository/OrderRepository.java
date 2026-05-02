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

    @Query("SELECT SUM(o.tongTien) FROM Order o WHERE o.trangThai = 'Hoàn thành' AND o.ngayDat BETWEEN :start AND :end")
    BigDecimal sumRevenueByDateRange(java.util.Date start, java.util.Date end);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.ngayDat BETWEEN :start AND :end")
    long countOrdersByDateRange(java.util.Date start, java.util.Date end);

    @Query("SELECT od.sanPhamSize.sanPham, SUM(od.soLuong) as totalSold " +
           "FROM OrderDetail od " +
           "WHERE od.order.trangThai = 'Hoàn thành' " +
           "GROUP BY od.sanPhamSize.sanPham " +
           "ORDER BY totalSold DESC")
    List<Object[]> findTopSellingProducts(org.springframework.data.domain.Pageable pageable);

    @Query("SELECT FUNCTION('DATE', o.ngayDat), SUM(o.tongTien), COUNT(o) " +
           "FROM Order o " +
           "WHERE o.trangThai = 'Hoàn thành' AND o.ngayDat >= :since " +
           "GROUP BY FUNCTION('DATE', o.ngayDat) " +
           "ORDER BY FUNCTION('DATE', o.ngayDat)")
    List<Object[]> findRevenueAndCountByDate(java.util.Date since);
}
