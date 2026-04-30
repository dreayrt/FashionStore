package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Conversation;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation,Integer> {
    Optional<Conversation> findByUser(TaiKhoan user );
    List<Conversation> findAllByStaff(TaiKhoan staff);
}
