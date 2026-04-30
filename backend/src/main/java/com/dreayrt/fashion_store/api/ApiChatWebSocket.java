package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.DTOs.ChatDTO;
import com.dreayrt.fashion_store.DTOs.MessageResponse;
import com.dreayrt.fashion_store.Model.Entities.Message;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.ChatService;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ApiChatWebSocket {
    @Autowired
    private ChatService chatService;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private com.dreayrt.fashion_store.repository.ConversationRepository conversationRepository;
    @Autowired
    private org.springframework.messaging.simp.SimpMessagingTemplate simpMessagingTemplate;
    @GetMapping("/history/{conversationId}")
    public List<MessageResponse> history(@PathVariable("conversationId") Integer conversationId){
        return chatService.getHistory(conversationId);
    }

    @PostMapping("/send")
    public MessageResponse send(@RequestBody ChatDTO dto, Principal principal){
        String username = principal.getName();

        TaiKhoan sender = taiKhoanRepository.findById(username)
                .orElseThrow();

        return chatService.sendMessage(dto, sender);
    }

    @GetMapping("/conversations")
    public List<com.dreayrt.fashion_store.DTOs.ConversationDTO> getConversations() {
        return chatService.getAllConversations();
    }

    @GetMapping("/my-conversation")
    public com.dreayrt.fashion_store.DTOs.ConversationDTO getMyConversation(Principal principal) {
        String username = principal.getName();
        TaiKhoan user = taiKhoanRepository.findById(username).orElseThrow();
        com.dreayrt.fashion_store.Model.Entities.Conversation c = chatService.getOrCreateConversation(user);
        com.dreayrt.fashion_store.DTOs.ConversationDTO dto = new com.dreayrt.fashion_store.DTOs.ConversationDTO();
        dto.setConversationId(c.getId());
        dto.setUsername(username);
        dto.setStatus(c.getStatus());
        return dto;
    }

    public static class BlockRequest {
        private String reason;
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }

    @PatchMapping("/{id}/block")
    public com.dreayrt.fashion_store.DTOs.MessageResponse blockConversation(@PathVariable Integer id, @RequestBody(required = false) BlockRequest req, Principal principal) {
        TaiKhoan staff = taiKhoanRepository.findById(principal.getName()).orElseThrow();
        String reason = req != null ? req.getReason() : "";
        com.dreayrt.fashion_store.DTOs.MessageResponse res = chatService.blockConversation(id, reason, staff);
        
        if (res != null) {
            com.dreayrt.fashion_store.Model.Entities.Conversation c = conversationRepository.findById(id).get();
            // Gửi tới khách hàng
            simpMessagingTemplate.convertAndSendToUser(c.getUser().getUsername(), "/queue/messages", res);
            
            // Gửi tới toàn bộ nhân viên/admin
            java.util.List<TaiKhoan> staffs = taiKhoanRepository.findByVaiTro("STAFF");
            for (TaiKhoan s : staffs) {
                simpMessagingTemplate.convertAndSendToUser(s.getUsername(), "/queue/messages", res);
            }
            java.util.List<TaiKhoan> admins = taiKhoanRepository.findByVaiTro("ADMIN");
            for (TaiKhoan a : admins) {
                simpMessagingTemplate.convertAndSendToUser(a.getUsername(), "/queue/messages", res);
            }
        }
        
        return res;
    }
}
