package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.R2Service;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class ApiUser {
    private final TaiKhoanRepository taiKhoanRepository;

    public ApiUser(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Autowired
    private R2Service  r2Service;

    @GetMapping
    public Map<String, String> getTaiKhoan(Authentication authentication) {
        TaiKhoan taiKhoan = getCurrentUser(authentication);

        Map<String, String> response = new HashMap<>();
        response.put("username", taiKhoan.getUsername());
        response.put("email", taiKhoan.getEmail());
        response.put("phone", taiKhoan.getPhone());
        response.put("diaChi", taiKhoan.getDiaChi());
        response.put("address", taiKhoan.getDiaChi());
        response.put("avatar", taiKhoan.getAvatar());

        return response;
    }

    @Value("${app.r2.public-url}")
    private String r2PublicUrl;

    @PatchMapping("/avatar")
    public Map<String, String> updateAvatar(Authentication authentication,
                                            @RequestParam("avatar") MultipartFile avatar) {
        TaiKhoan tk = getCurrentUser(authentication);

        try {
            // validate nhanh
            if (avatar.isEmpty() || !avatar.getContentType().startsWith("image/")) {
                throw new RuntimeException("File không hợp lệ");
            }

            // tạo tên file
            String ext = avatar.getOriginalFilename()
                    .substring(avatar.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID() + ext;

            String key = "avatar/" + fileName;

            // upload lên R2
            r2Service.uploadFile(key, avatar);

            // lưu URL public
            String avatarUrl = r2PublicUrl + "/" + key;

            tk.setAvatar(avatarUrl);
            taiKhoanRepository.save(tk);

            return Map.of("avatar", avatarUrl);

        } catch (Exception e) {
            throw new RuntimeException("Upload avatar lỗi", e);
        }
    }

    private TaiKhoan getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "login required");
        }

        return taiKhoanRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "username not found"));
    }
}
