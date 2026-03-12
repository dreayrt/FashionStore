package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.CommentRequest;
import com.dreayrt.fashion_store.Model.Entities.Comment;
import com.dreayrt.fashion_store.Model.Entities.SanPham;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.CommentRepository;
import com.dreayrt.fashion_store.repository.SanPhamRepository;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {
    private final CommentRepository commentRepository;
    private final SanPhamRepository sanPhamRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    public UserService(CommentRepository commentRepository, SanPhamRepository sanPhamRepository, TaiKhoanRepository taiKhoanRepository) {
        this.commentRepository = commentRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Transactional
    public void createComment(CommentRequest commentRequest,String username) {
        Comment comment = new Comment();

        comment.setNoiDung(commentRequest.getNoiDung());
        comment.setDanhGia(commentRequest.getRating());
        comment.setNgayBinhLuan(new Date());
        SanPham sanPham = sanPhamRepository.findById(commentRequest.getMaSanPham()).orElseThrow(()->new RuntimeException("khong tim thay ma san pham"));
        comment.setSanPham(sanPham);
        TaiKhoan tk=taiKhoanRepository.findByUsername(username).orElseThrow();
        comment.setUser(tk);
        commentRepository.save(comment);
    }
}
