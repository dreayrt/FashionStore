package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment>findBySanPham_MaSanPham(String maSanPham);
}
