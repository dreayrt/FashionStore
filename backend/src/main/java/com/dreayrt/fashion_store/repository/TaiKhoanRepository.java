package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.OtpLog;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, String> {
    Optional<TaiKhoan> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    //neu loi (NPE) thi dung wrapper Optional
    Optional<TaiKhoan> findByEmail(String email);
    
    List<TaiKhoan> findByVaiTro(String vaiTro);
    
    long countByVaiTro(String vaiTro);

    org.springframework.data.domain.Page<TaiKhoan> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email, org.springframework.data.domain.Pageable pageable);
}
