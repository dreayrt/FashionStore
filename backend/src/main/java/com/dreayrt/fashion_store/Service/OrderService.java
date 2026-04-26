package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.repository.DisplayItemRepository;
import com.dreayrt.fashion_store.DTOs.OrderRequest;
import com.dreayrt.fashion_store.Model.Entities.*;
import com.dreayrt.fashion_store.repository.OrderDetailRepository;
import com.dreayrt.fashion_store.repository.OrderRepository;
import com.dreayrt.fashion_store.repository.ShoppingCartDetailRepository;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final TaiKhoanRepository taiKhoanRepository;

    public OrderService(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Autowired
    private ShoppingCartDetailRepository shoppingCartDetailRepository;
    @Autowired
    private DisplayItemRepository displayItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Transactional
    public Order createOrder(Authentication authentication, OrderRequest request) {
        String username = authentication.getName();
        TaiKhoan taikhoan= taiKhoanRepository.findByUsername(username).orElseThrow(()->new RuntimeException("username not found"));

        Order order = new Order();
        order.setNgayDat(new Date());
        order.setTrangThai("Đang Xử Lý");
        order.setTaiKhoan(taikhoan);
        order.setTenNguoiNhan(request.getRecipientName());
        order.setSoDienThoai(request.getRecipientPhone());
        order.setDiaChi(request.getRecipientAddress());
        order.setPhuongThucThanhToan(request.getPaymentMethod());

        BigDecimal totalPrice = BigDecimal.ZERO;

        List<ShoppingCartDetail> items= shoppingCartDetailRepository.findByShoppingCart_Taikhoan_Username(username);
        for  (ShoppingCartDetail item : items) {
            int soLuong=item.getSoLuong();
            Integer maSPSize= item.getSanPhamSize().getMaSPSize();
            DisplayItem giaHangTrungBay= displayItemRepository.findById(maSPSize).orElseThrow(()->new RuntimeException("item not found"));
            BigDecimal giaBan=giaHangTrungBay.getGiaBan();
            totalPrice = totalPrice.add(giaBan.multiply(new BigDecimal(soLuong)));
        }
        order.setTongTien(totalPrice);
        orderRepository.save(order);

        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        for  (ShoppingCartDetail item : items) {
            DisplayItem giaSanPham= displayItemRepository.findById(item.getSanPhamSize().getMaSPSize()).orElseThrow(()->new RuntimeException("item not found"));

            OrderDetail detail= new OrderDetail();

            OrderDetailID DetailID = new OrderDetailID();
            DetailID.setMaDonHang(order.getMaDonHang());
            DetailID.setMaSanPhamSize(item.getSanPhamSize().getMaSPSize());

            detail.setOrderDetailID(DetailID);
            detail.setSoLuong(item.getSoLuong());
            detail.setDonGia(giaSanPham.getGiaBan());
            detail.setOrder(order);
            detail.setSanPhamSize(item.getSanPhamSize());

            orderDetailRepository.save(detail);

        }

        shoppingCartDetailRepository.deleteAll(items);
        return order;
    }
}
