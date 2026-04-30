package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Conversation;
import com.dreayrt.fashion_store.Model.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    List<Message> findByConversationIdOrderByCreatedAtAsc(Integer conversationId);

}
