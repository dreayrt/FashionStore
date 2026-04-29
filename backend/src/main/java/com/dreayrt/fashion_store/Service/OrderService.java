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
import java.util.Map;
import java.util.stream.Collectors;

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
        TaiKhoan taikhoan = taiKhoanRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("username not found"));


        Order order = new Order();
        order.setNgayDat(new Date());
        if ("SEPAY".equalsIgnoreCase(request.getPaymentMethod())) {
            order.setTrangThai("Chờ Thanh Toán");
        } else {
            order.setTrangThai("Chờ Xác Nhận");
        }
        order.setTaiKhoan(taikhoan);
        order.setTenNguoiNhan(request.getRecipientName());
        order.setSoDienThoai(request.getRecipientPhone());
        order.setDiaChi(request.getRecipientAddress());
        order.setPhuongThucThanhToan(request.getPaymentMethod());
        order.setDaThanhToan(false);

        BigDecimal totalPrice = BigDecimal.ZERO;

        List<ShoppingCartDetail> items = shoppingCartDetailRepository.findByShoppingCart_Taikhoan_Username(username);
        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        //lay toan bo size tranh N+1 query
        List<Integer> sizes = items.stream().map(i -> i.getSanPhamSize().getMaSPSize()).distinct().toList();

        //query 1 lan duy nhat
        List<DisplayItem> displayItems= displayItemRepository.findAllById(sizes);

        // 4. Convert sang Map
        Map<Integer, DisplayItem> displayItemMap = displayItems.stream()
                .collect(Collectors.toMap(DisplayItem::getMaSPSize, d -> d));

        for (ShoppingCartDetail item : items) {
            DisplayItem displayItem = displayItemMap.get(item.getSanPhamSize().getMaSPSize());
            BigDecimal giaBan;
            
            if (displayItem != null) {
                giaBan = displayItem.getGiaBan();
            } else {
                // Fallback to product base price if not on display
                giaBan = item.getSanPhamSize().getSanPham().getGiaSanPham();
            }

            int soLuong = item.getSoLuong();
            totalPrice = totalPrice.add(giaBan.multiply(new BigDecimal(soLuong)));
        }
        
        // Cộng thêm phí vận chuyển cố định 30.000đ
        BigDecimal shippingFee = new BigDecimal("30000");
        totalPrice = totalPrice.add(shippingFee);
        
        order.setTongTien(totalPrice);
        orderRepository.save(order);


        for (ShoppingCartDetail item : items) {
           DisplayItem displayItem = displayItemMap.get(item.getSanPhamSize().getMaSPSize());

            OrderDetail detail = new OrderDetail();

            OrderDetailID DetailID = new OrderDetailID();
            DetailID.setMaDonHang(order.getMaDonHang());
            DetailID.setMaSanPhamSize(item.getSanPhamSize().getMaSPSize());

            detail.setOrderDetailID(DetailID);
            detail.setSoLuong(item.getSoLuong());
            BigDecimal giaBan = (displayItem != null) ? displayItem.getGiaBan() : item.getSanPhamSize().getSanPham().getGiaSanPham();
            detail.setDonGia(giaBan);
            detail.setOrder(order);
            detail.setSanPhamSize(item.getSanPhamSize());

            orderDetailRepository.save(detail);

        }

        if (!"SEPAY".equalsIgnoreCase(request.getPaymentMethod())) {
            shoppingCartDetailRepository.deleteAll(items);
        }
        return order;
    }

    @Transactional
    public void deleteOrderFully(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            List<OrderDetail> details = orderDetailRepository.findByOrder(order);
            orderDetailRepository.deleteAll(details);
            orderRepository.delete(order);
        }
    }

    @Transactional
    public void completeSepayOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setDaThanhToan(true);
            order.setTrangThai("Chờ Xác Nhận");
            orderRepository.save(order);
            
            String username = order.getTaiKhoan().getUsername();
            List<ShoppingCartDetail> items = shoppingCartDetailRepository.findByShoppingCart_Taikhoan_Username(username);
            shoppingCartDetailRepository.deleteAll(items);
        }
    }
}
