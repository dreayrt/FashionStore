package com.dreayrt.fashion_store.Interceptor;

import com.dreayrt.fashion_store.Service.VisitLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class VisitInterceptor implements HandlerInterceptor {

    @Autowired
    private VisitLogService visitLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Chỉ log các request GET (truy cập trang)
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String path = request.getRequestURI();
            
            // Bỏ qua các file tĩnh, api, hoặc dashboard admin (tùy nhu cầu)
            if (!path.contains(".") && !path.startsWith("/api/") && !path.startsWith("/Avatar/")) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = (auth != null && auth.isAuthenticated()) ? auth.getName() : null;
                
                visitLogService.logVisit(username, path);
            }
        }
        return true;
    }
}
