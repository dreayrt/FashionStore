package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.DTOs.ChatDTO;
import com.dreayrt.fashion_store.DTOs.MessageResponse;
import com.dreayrt.fashion_store.Model.Entities.Conversation;
import com.dreayrt.fashion_store.Model.Entities.Message;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.ConversationRepository;
import com.dreayrt.fashion_store.repository.MessageRepository;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public Conversation getOrCreateConversation(TaiKhoan user) {
        return conversationRepository.findByUser(user).orElseGet(() -> {
            Conversation conversation = new Conversation();
            conversation.setUser(user);
            return conversationRepository.save(conversation);
        });
    }
    public Message saveMessage(TaiKhoan sender, Conversation conversation,String content) {
        Message message = new Message();
        message.setConversation(conversation);
        message.setContent(content);
        message.setSender(sender);
        return messageRepository.save(message);
    }

    public List<MessageResponse> getHistory(Integer conversationId) {
        return messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId).stream().map(msg->{
            MessageResponse msgResponse= new MessageResponse();
            msgResponse.setSender(msg.getSender().getUsername());
            msgResponse.setContent(msg.getContent());
            msgResponse.setCreatedAt(msg.getCreatedAt());
            return msgResponse;
        }).toList();
    }

    public MessageResponse sendMessage(ChatDTO dto, TaiKhoan sender) {
        Conversation conversation;
        if (dto.getConversationId() != null) {
            conversation = conversationRepository.findById(dto.getConversationId())
                    .orElseThrow(() -> new RuntimeException("conversation id not found"));
        } else {
            conversation = getOrCreateConversation(sender);
        }

        if ("BLOCKED".equals(conversation.getStatus())) {
            throw new IllegalArgumentException("Hội thoại đã bị khóa.");
        }

        Message msg = new Message();
        msg.setConversation(conversation);
        msg.setSender(sender);
        msg.setContent(dto.getContent());

        messageRepository.save(msg);

        MessageResponse res = new MessageResponse();
        res.setSender(sender.getUsername());
        res.setContent(msg.getContent());
        res.setCreatedAt(msg.getCreatedAt());

        return res;
    }

    public List<com.dreayrt.fashion_store.DTOs.ConversationDTO> getAllConversations() {
        return conversationRepository.findAll().stream().map(c -> {
            com.dreayrt.fashion_store.DTOs.ConversationDTO dto = new com.dreayrt.fashion_store.DTOs.ConversationDTO();
            dto.setConversationId(c.getId());
            dto.setStatus(c.getStatus());
            if (c.getUser() != null) {
                dto.setUsername(c.getUser().getUsername());
            }
            return dto;
        }).toList();
    }

    public MessageResponse blockConversation(Integer id, String reason, TaiKhoan staff) {
        Conversation c = conversationRepository.findById(id).orElseThrow(() -> new RuntimeException("Conversation not found"));
        c.setStatus("BLOCKED".equals(c.getStatus()) ? "OPEN" : "BLOCKED");
        conversationRepository.save(c);

        if ("BLOCKED".equals(c.getStatus())) {
            Message systemMsg = new Message();
            systemMsg.setConversation(c);
            systemMsg.setSender(staff); // we use the staff as sender to avoid null constraint
            systemMsg.setContent("Hệ thống: Hội thoại đã bị khóa. Lý do: " + (reason != null && !reason.trim().isEmpty() ? reason : "Không có lý do cụ thể"));
            messageRepository.save(systemMsg);

            MessageResponse res = new MessageResponse();
            res.setSender("Hệ thống");
            res.setContent(systemMsg.getContent());
            res.setCreatedAt(systemMsg.getCreatedAt());
            return res;
        } else {
            // Gửi tin nhắn thông báo đã mở khóa
            Message systemMsg = new Message();
            systemMsg.setConversation(c);
            systemMsg.setSender(staff);
            systemMsg.setContent("Hệ thống: Hội thoại đã được mở khóa.");
            messageRepository.save(systemMsg);

            MessageResponse res = new MessageResponse();
            res.setSender("Hệ thống");
            res.setContent(systemMsg.getContent());
            res.setCreatedAt(systemMsg.getCreatedAt());
            return res;
        }
    }
}
