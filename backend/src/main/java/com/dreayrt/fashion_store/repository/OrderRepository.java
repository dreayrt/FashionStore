package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByMaDonHangAndTaiKhoan_Username(Integer maDonHang,String userName);
}
