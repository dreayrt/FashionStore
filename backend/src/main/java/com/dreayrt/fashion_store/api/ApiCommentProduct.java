package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.DTOs.CommentRequest;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.UserService;
import com.dreayrt.fashion_store.repository.OrderRepository;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/commentProduct")
public class ApiCommentProduct {
    private final UserService userService;
    private final TaiKhoanRepository taiKhoanRepository;
    private final OrderRepository orderRepository;

    public ApiCommentProduct(UserService userService, TaiKhoanRepository taiKhoanRepository, OrderRepository orderRepository) {
        this.userService = userService;
        this.taiKhoanRepository = taiKhoanRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping()
    public ResponseEntity<?> addComment(
            @Valid @RequestBody CommentRequest commentRequest,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError e : bindingResult.getFieldErrors()) {
                errors.put(e.getField(), e.getDefaultMessage());
            }

            Map<String, Object> body = new HashMap<>();
            body.put("message", "Du lieu khong hop le");
            body.put("errors", errors);

            return ResponseEntity.badRequest().body(body);
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Ban chua dang nhap");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        TaiKhoan tk = taiKhoanRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ban chua dang nhap"));

        // Kiểm tra xem người dùng đã mua sản phẩm này và đơn hàng đã hoàn thành chưa
        boolean hasBought = orderRepository.existsByTaiKhoan_UsernameAndTrangThaiAndOrderDetail_SanPhamSize_SanPham_MaSanPham(
                tk.getUsername(), "Hoàn thành", commentRequest.getMaSanPham()
        );

        if (!hasBought) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Bạn chỉ có thể đánh giá sản phẩm sau khi đã mua và đơn hàng được hoàn thành.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
        }

        userService.createComment(commentRequest, tk.getUsername());

        // Lấy lại avatar mới nhất từ DB để tránh cache
        String currentAvatar = taiKhoanRepository.findByUsername(tk.getUsername())
                .map(TaiKhoan::getAvatar)
                .orElse(null);

        Map<String, Object> response = new HashMap<>();
        response.put("username", tk.getUsername());
        response.put("avatar", currentAvatar);
        response.put("noiDung", commentRequest.getNoiDung());
        response.put("rating", commentRequest.getRating());
        response.put("maSanPham", commentRequest.getMaSanPham());
        response.put("ngayBinhLuan", new Date());

        return ResponseEntity.ok(response);
    }
}
