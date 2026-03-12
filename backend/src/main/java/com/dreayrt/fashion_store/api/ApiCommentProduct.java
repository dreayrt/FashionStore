package com.dreayrt.fashion_store.api;

import com.dreayrt.fashion_store.DTOs.CommentRequest;
import com.dreayrt.fashion_store.Model.Entities.Comment;
import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/commentProduct")
public class ApiCommentProduct {
    private final UserService userService;

    public ApiCommentProduct(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
//    ResponseEntity là đối tượng đại diện cho HTTP Response.
//    Nó cho phép bạn điều khiển:HTTP Status Code, Response Body, Response Header
//    Ví dụ HTTP response thực tế:
//    HTTP/1.1 200 OK
//    Content-Type: application/json
//    Comment thành công

//    Trong Spring Boot:
//    return ResponseEntity.ok("Comment thành công");
    public ResponseEntity<?> addComment(
            @Valid @RequestBody CommentRequest commentRequest,
            BindingResult bindingResult,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
//            FieldError là một object của Spring dùng để mô tả lỗi validation của một field cụ thể trong form/DTO.
            for (FieldError e : bindingResult.getFieldErrors()) {
                errors.put(e.getField(), e.getDefaultMessage());
            }

            Map<String, Object> body = new HashMap<>();
            body.put("message", "Dữ liệu không hợp lệ");
            body.put("errors", errors);

            return ResponseEntity.badRequest().body(body); //400
        }

        TaiKhoan tk = (TaiKhoan) session.getAttribute("user");
        if (tk == null) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Bạn chưa đăng nhập");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        userService.createComment(commentRequest, tk.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("username", tk.getUsername());
        response.put("noiDung", commentRequest.getNoiDung());
        response.put("rating", commentRequest.getRating());
        response.put("maSanPham", commentRequest.getMaSanPham());
        response.put("ngayBinhLuan", new Date());

        return ResponseEntity.ok(response);
    }
}

//200	OK
//201	Created
//400	Bad Request
//401	Unauthorized
//403	Forbidden
//404	Not Found
//500	Server Error