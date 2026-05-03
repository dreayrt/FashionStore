package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.repository.*;
import com.dreayrt.fashion_store.exception.AppException;
import com.dreayrt.fashion_store.DTOs.OrderRequest;
import com.dreayrt.fashion_store.DTOs.OrderPreviewDTO;
import com.dreayrt.fashion_store.Model.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private VoucherRepository voucherRepository;

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

    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

    public OrderPreviewDTO previewOrder(Authentication authentication, String voucherCode) {
        String username = authentication.getName();
        List<ShoppingCartDetail> items = shoppingCartDetailRepository.findByShoppingCart_Taikhoan_Username(username);
        
        OrderPreviewDTO preview = new OrderPreviewDTO();
        BigDecimal subtotal = BigDecimal.ZERO;
        int totalQty = 0;

        //lay toan bo size tranh N+1 query
        List<Integer> sizes = items.stream().map(i -> i.getSanPhamSize().getMaSPSize()).distinct().toList();
        List<DisplayItem> displayItems = displayItemRepository.findAllById(sizes);
        Map<Integer, DisplayItem> displayItemMap = displayItems.stream()
                .collect(Collectors.toMap(DisplayItem::getMaSPSize, d -> d));

        for (ShoppingCartDetail item : items) {
            DisplayItem displayItem = displayItemMap.get(item.getSanPhamSize().getMaSPSize());
            BigDecimal giaBan = (displayItem != null) ? displayItem.getGiaBan() : item.getSanPhamSize().getSanPham().getGiaSanPham();
            int soLuong = item.getSoLuong();
            subtotal = subtotal.add(giaBan.multiply(new BigDecimal(soLuong)));
            totalQty += soLuong;
        }

        BigDecimal shippingFee = new BigDecimal("30000");
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal totalBeforeDiscount = subtotal.add(shippingFee);
        
        preview.setSubtotal(subtotal);
        preview.setTotalQuantity(totalQty);
        preview.setShippingFee(shippingFee);
        preview.setVoucherValid(true);

        if (voucherCode != null && !voucherCode.isEmpty()) {
            try {
                Voucher voucher = voucherRepository.findById(voucherCode).orElseThrow(() -> new RuntimeException("Voucher không tồn tại"));

                if (voucher.getNgayHetHan().isBefore(LocalDateTime.now())) {
                    throw new RuntimeException("Voucher đã hết hạn");
                }
                if (totalBeforeDiscount.compareTo(voucher.getGiaTriToiThieu()) < 0) {
                    throw new RuntimeException("Đơn hàng chưa đạt giá trị tối thiểu để áp dụng Voucher");
                }

                if ("percent".equalsIgnoreCase(voucher.getLoai())) {
                    discount = totalBeforeDiscount.multiply(voucher.getGiaTri()).divide(BigDecimal.valueOf(100));
                } else {
                    discount = voucher.getGiaTri();
                }

                if (voucher.getSoLuong() == 0) {
                    throw new RuntimeException("Voucher đã hết lượt sử dụng");
                }
                preview.setVoucherCode(voucherCode);
            } catch (Exception e) {
                preview.setVoucherValid(false);
                preview.setErrorMessage(e.getMessage());
                discount = BigDecimal.ZERO;
            }
        }

        preview.setDiscount(discount);
        preview.setFinalTotal(totalBeforeDiscount.subtract(discount));
        
        return preview;
    }

    @Transactional
    public Order createOrder(Authentication authentication, OrderRequest request) {
        String username = authentication.getName();
        TaiKhoan taikhoan = taiKhoanRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("username not found"));

        List<ShoppingCartDetail> items = shoppingCartDetailRepository.findByShoppingCart_Taikhoan_Username(username);
        if (items.isEmpty()) {
            throw new AppException("Giỏ hàng đang trống");
        }

        // 1. Kiểm tra tồn kho trước khi tạo đơn
        for (ShoppingCartDetail item : items) {
            SanPhamSize spSize = item.getSanPhamSize();
            if (spSize.getKho() == null || spSize.getKho().getSoLuong() < item.getSoLuong()) {
                throw new AppException("Sản phẩm " + spSize.getSanPham().getTenSanPham() + " (Size: " + spSize.getSize() + ") không đủ số lượng trong kho.");
            }
        }

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

        //lay toan bo size tranh N+1 query
        List<Integer> sizes = items.stream().map(i -> i.getSanPhamSize().getMaSPSize()).distinct().toList();
        List<DisplayItem> displayItems= displayItemRepository.findAllById(sizes);
        Map<Integer, DisplayItem> displayItemMap = displayItems.stream()
                .collect(Collectors.toMap(DisplayItem::getMaSPSize, d -> d));

        for (ShoppingCartDetail item : items) {
            DisplayItem displayItem = displayItemMap.get(item.getSanPhamSize().getMaSPSize());
            BigDecimal giaBan = (displayItem != null) ? displayItem.getGiaBan() : item.getSanPhamSize().getSanPham().getGiaSanPham();
            int soLuong = item.getSoLuong();
            totalPrice = totalPrice.add(giaBan.multiply(new BigDecimal(soLuong)));
        }

        BigDecimal shippingFee = new BigDecimal("30000");
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal totalBeforeDiscount = totalPrice.add(shippingFee);

        if(request.getVoucherCode()!=null && !request.getVoucherCode().isEmpty()) {
            Voucher voucher=voucherRepository.findById(request.getVoucherCode()).orElseThrow(() -> new AppException("Mã giảm giá không tồn tại"));
            if(voucher.getNgayHetHan().isBefore(LocalDateTime.now())){
                throw new AppException("Voucher đã hết hạn");
            }
            if(totalBeforeDiscount.compareTo(voucher.getGiaTriToiThieu()) < 0){
                throw new AppException("Đơn hàng chưa đạt giá trị tối thiểu để áp dụng Voucher");
            }
            if("percent".equalsIgnoreCase(voucher.getLoai())){
                discount = totalBeforeDiscount.multiply(voucher.getGiaTri()).divide(BigDecimal.valueOf(100));
            } else {
                discount = voucher.getGiaTri();
            }
            if(voucher.getSoLuong()==0){
                throw new AppException("Voucher đã hết lượt sử dụng");
            }
            voucher.setSoLuong(voucher.getSoLuong() - 1);
            order.setVoucher(voucher);
        }

        BigDecimal finalTotal = totalBeforeDiscount.subtract(discount);
        order.setTongTien(finalTotal);
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

            // 2. Trừ số lượng trong kho
            SanPhamSize spSize = item.getSanPhamSize();
            Kho kho = spSize.getKho();
            kho.setSoLuong(kho.getSoLuong() - item.getSoLuong());
            kho.setNgayCapNhat(new Date());
            sanPhamSizeRepository.save(spSize);
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
            
            // Hoàn lại số lượng vào kho
            for (OrderDetail detail : details) {
                SanPhamSize spSize = detail.getSanPhamSize();
                if (spSize != null && spSize.getKho() != null) {
                    Kho kho = spSize.getKho();
                    kho.setSoLuong(kho.getSoLuong() + detail.getSoLuong());
                    kho.setNgayCapNhat(new Date());
                    sanPhamSizeRepository.save(spSize);
                }
            }

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
