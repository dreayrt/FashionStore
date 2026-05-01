package com.dreayrt.fashion_store.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.dreayrt.fashion_store.Interceptor.VisitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

//Mục đích của class này: Khi browser truy cập URL /Avatar/... thì đừng tìm trong project, hãy lấy file từ thư mục /opt/fashionstore/avatar/ trên server(vps).
//WebMvcConfigurer cho phép bạn custom cách Spring MVC hoạt động.
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    @Autowired
    private VisitInterceptor visitInterceptor;

//    Method này dùng để đăng ký các đường dẫn static mới cho Spring Boot.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        tat ca cac url bat dau bang /Avatar/ thi map toi folder that tren server
        registry.addResourceHandler("/Avatar/**").addResourceLocations("file:/opt/fashionstore/avatar/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/dashboard/api/**", "/actuator/**", "/static/**", "/css/**", "/js/**", "/img/**");
    }
}
