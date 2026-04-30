package com.dreayrt.fashion_store.Controller;

import com.dreayrt.fashion_store.DTOs.ChatDTO;
import com.dreayrt.fashion_store.DTOs.MessageResponse;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.ChatService;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @MessageMapping("/chat.send")
    public void send(ChatDTO chatDTO, Principal principal) {
        String username = principal.getName();
        TaiKhoan sender = taiKhoanRepository.findById(username)
                .orElseThrow();
        try {
            MessageResponse response = chatService.sendMessage(chatDTO, sender);

            if ("STAFF".equalsIgnoreCase(sender.getVaiTro()) || "ADMIN".equalsIgnoreCase(sender.getVaiTro())) {
                // Staff/Admin replying to a customer
                simpMessagingTemplate.convertAndSendToUser(
                        chatDTO.getReceiver(), //gui cho ai
                        "/queue/messages", // kenh nao
                        response //content
                );
            } else {
                // Customer sending to staff
                java.util.List<TaiKhoan> staffs = taiKhoanRepository.findByVaiTro("STAFF");
                for (TaiKhoan staff : staffs) {
                    simpMessagingTemplate.convertAndSendToUser(
                            staff.getUsername(),
                            "/queue/messages",
                            response
                    );
                }
//                java.util.List<TaiKhoan> admins = taiKhoanRepository.findByVaiTro("ADMIN");
//                for (TaiKhoan admin : admins) {
//                    simpMessagingTemplate.convertAndSendToUser(
//                            admin.getUsername(),
//                            "/queue/messages",
//                            response
//                    );
//                }
            }
        } catch (IllegalArgumentException e) {
            MessageResponse errorRes = new MessageResponse();
            errorRes.setSender("Hệ thống");
            errorRes.setContent(e.getMessage());
            errorRes.setCreatedAt(java.time.LocalDateTime.now());
            simpMessagingTemplate.convertAndSendToUser(
                    username,
                    "/queue/messages",
                    errorRes
            );
        }

    }
}
