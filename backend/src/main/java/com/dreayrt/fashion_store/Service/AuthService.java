package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.RegisterRequest;
import com.dreayrt.fashion_store.HashUtil;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Transactional
    public TaiKhoan Login(String username, String password) {
        TaiKhoan taiKhoan =taiKhoanRepository.findByUsername(username);
        if (taiKhoan == null) {
           throw new RuntimeException("Tài Khoản không tồn tại");
        }
        if("Locked".equals(taiKhoan.getTrangThai())){
            throw new RuntimeException("Tài Khoản Đang Bị Khóa");
        }
        String passWordHash= HashUtil.sha256(password);

        if(!passWordHash.equals(taiKhoan.getPassword())){
            throw new RuntimeException("Sai Mật Khẩu");
        }
        taiKhoan.setTrangThai("Online");
        taiKhoanRepository.save(taiKhoan);
        return taiKhoan;
    }
    @Transactional
    public void Logout(String username){
        TaiKhoan taiKhoan =taiKhoanRepository.findByUsername(username);
        if(taiKhoan != null && !"Locked".equals(taiKhoan.getTrangThai())){
            taiKhoan.setTrangThai("Offline");
            taiKhoanRepository.save(taiKhoan);
        }
    }

    @Transactional
    public void Register(RegisterRequest registerRequest){
        if(taiKhoanRepository.existsByUsername(registerRequest.getUsername())){
            throw new RuntimeException("Tài Khoản Đã Tồn Tại");
        }
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            throw new RuntimeException("Mật Khẩu Không Khớp");
        }
        if(taiKhoanRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Email Này Đã Được Sử Dụng");
        }
        if(taiKhoanRepository.existsByPhone(registerRequest.getPhone())){
            throw new RuntimeException("Số Điện Thoại Này Đã Được Đăng Ký");
        }
        String passwordHash=HashUtil.sha256(registerRequest.getPassword());
        TaiKhoan taiKhoan =new TaiKhoan();
        taiKhoan.setUsername(registerRequest.getUsername());
        taiKhoan.setPassword(passwordHash);
        taiKhoan.setTrangThai("Offline");
        taiKhoan.setEmail(registerRequest.getEmail());
        taiKhoan.setDiaChi(registerRequest.getAddress());
        taiKhoan.setPhone(registerRequest.getPhone());

        String email=registerRequest.getEmail();
        String emailPrefix =email.split("@")[0].toLowerCase();
        if(emailPrefix.startsWith("admin")){
            taiKhoan.setVaiTro("ADMIN");
        }else if(emailPrefix.startsWith("staff")){
            taiKhoan.setVaiTro("STAFF");
        }else{
            taiKhoan.setVaiTro("USER");
        }
        taiKhoanRepository.save(taiKhoan);
    }

}
