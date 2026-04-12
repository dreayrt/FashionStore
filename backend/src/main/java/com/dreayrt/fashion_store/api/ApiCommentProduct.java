package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.DTOs.CommentRequest;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.UserService;
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

    public ApiCommentProduct(UserService userService, TaiKhoanRepository taiKhoanRepository) {
        this.userService = userService;
        this.taiKhoanRepository = taiKhoanRepository;
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

        userService.createComment(commentRequest, tk.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("username", tk.getUsername());
        response.put("noiDung", commentRequest.getNoiDung());
        response.put("rating", commentRequest.getRating());
        response.put("maSanPham", commentRequest.getMaSanPham());
        response.put("ngayBinhLuan", new Date());

        return ResponseEntity.ok(response);
    }
}
