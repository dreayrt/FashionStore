package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.Model.Entities.*;
import com.dreayrt.fashion_store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private ShoppingCartDetailRepository shoppingCartDetailRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private SanPhamSizeRepository sanPhamSizeRepository;

    public ShoppingCart getOrCreate(String username) {
        return shoppingCartRepository.findByTaikhoan_Username(username)
                .orElseGet(() -> {
                    ShoppingCart c = new ShoppingCart();
                    TaiKhoan tk = taiKhoanRepository.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("User khong ton tai"));
                    c.setTaikhoan(tk);
                    return shoppingCartRepository.save(c);
                });
    }

    public void addOrUpdate(String username, String productId, String size) {
        ShoppingCart cart = getOrCreate(username);

        SanPhamSize spSize = sanPhamSizeRepository
                .findBySanPham_MaSanPhamAndSize(productId, size)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy size"));

        Optional<ShoppingCartDetail> item =
                shoppingCartDetailRepository
                        .findByShoppingCart_Taikhoan_UsernameAndSanPhamSize_MaSPSize(
                                username, spSize.getMaSPSize()
                        );

        if (item.isPresent()) {
            ShoppingCartDetail c = item.get();
            c.setSoLuong(c.getSoLuong() + 1);
            shoppingCartDetailRepository.save(c);
        } else {
            ShoppingCartDetail c = new ShoppingCartDetail();
            c.setSoLuong(1);
            c.setShoppingCart(cart);
            c.setSanPhamSize(spSize);
            shoppingCartDetailRepository.save(c);
        }
    }

    public void incress(String username, String ProductId,String size) {
        SanPhamSize spSize = sanPhamSizeRepository
                .findBySanPham_MaSanPhamAndSize(ProductId, size)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy size"));

        ShoppingCartDetail item =
                shoppingCartDetailRepository
                        .findByShoppingCart_Taikhoan_UsernameAndSanPhamSize_MaSPSize(
                                username, spSize.getMaSPSize()
                        )
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        item.setSoLuong(item.getSoLuong() + 1);
        shoppingCartDetailRepository.save(item);
    }

    public void decress(String username, String productId, String size) {

        SanPhamSize spSize = sanPhamSizeRepository
                .findBySanPham_MaSanPhamAndSize(productId, size)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy size"));

        ShoppingCartDetail item =
                shoppingCartDetailRepository
                        .findByShoppingCart_Taikhoan_UsernameAndSanPhamSize_MaSPSize(
                                username, spSize.getMaSPSize()
                        )
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (item.getSoLuong() > 1) {
            item.setSoLuong(item.getSoLuong() - 1);
            shoppingCartDetailRepository.save(item);
        } else {
            shoppingCartDetailRepository.delete(item);
        }
    }
}
