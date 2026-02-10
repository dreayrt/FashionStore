package com.dreayrt.fashion_store.Config;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

//authentication dai dien cho doi tuong login vao he thong chua cac thong tin nhu:
//        role,username,da xac thuc hay chua,thong tin chi tiet
        String username = authentication.getName();
        TaiKhoan user = taiKhoanRepository.findByUsername(username).orElse(null);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

