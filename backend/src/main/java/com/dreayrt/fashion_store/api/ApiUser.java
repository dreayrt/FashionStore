package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
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

@RestController
@RequestMapping("api/user")
public class ApiUser {
    private final TaiKhoanRepository taiKhoanRepository;

    public ApiUser(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

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

    @PatchMapping("/avatar")
    public Map<String, String> updateAvatar(Authentication authentication, @RequestParam("avatar") MultipartFile avatar)
            throws IOException {
        TaiKhoan tk = getCurrentUser(authentication);
        String filename = "avatar-" + System.currentTimeMillis() + ".jpg";

        Path uploadPath = Paths.get("/opt/fashionstore/avatar");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);

        Files.copy(avatar.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        tk.setAvatar("/Avatar/" + filename);
        taiKhoanRepository.save(tk);
        Map<String, String> response = new HashMap<>();
        response.put("avatar", tk.getAvatar());
        return response;
    }

    private TaiKhoan getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "login required");
        }

        return taiKhoanRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "username not found"));
    }
}
