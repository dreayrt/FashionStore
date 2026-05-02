package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.DTOs.LoginRequest;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Util.JwtUtil;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class ApiAuthentication {
    private final AuthenticationManager authenticationManager;

    public ApiAuthentication(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Remember: " + request.getRemember());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (LockedException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Tài khoản của bạn đã bị khóa!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
        catch (BadCredentialsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Sai tài khoản hoặc mật khẩu!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        TaiKhoan user = taiKhoanRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("Username not found"));
        String role = user.getVaiTro();

        String accessToken = jwtUtil.generateAccessToken(request.getUsername(), role);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("accessToken", accessToken);
        responseData.put("role", role);
        if (Boolean.TRUE.equals(request.getRemember())) {
            String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());
            responseData.put("refreshToken", refreshToken);
        }
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null) {
            throw new RuntimeException("Missing refresh token");
        }
        try {
            String type = jwtUtil.extractType(refreshToken);
            if (!"refresh_token".equals(type)) {
                throw new RuntimeException("Invalid refresh token");
            }
            String username = jwtUtil.extractUsername(refreshToken);
            TaiKhoan user= taiKhoanRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Username not found"));
            String role= user.getVaiTro();
            String newAccessToken = jwtUtil.generateAccessToken(username,role);
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Refresh token invalid hoặc hết hạn");
        }
    }

}
