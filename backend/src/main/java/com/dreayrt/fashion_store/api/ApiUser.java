package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public Map<String, String> getTaiKhoan(@RequestParam("username") String username) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "username not found"));

//        Dung map de tra ra nhung field can thiet, tranh tra ra cac  nhay cam nhu password,...
//        ne luon tinh trang infinite loop, khong can dung @JsonIgnore,...
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
    public Map<String, String> updateAvatar(@RequestParam("username") String username, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        TaiKhoan tk = taiKhoanRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "username not found"));
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

}
