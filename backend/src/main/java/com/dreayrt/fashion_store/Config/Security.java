package com.dreayrt.fashion_store.Config;

import com.dreayrt.fashion_store.Service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import com.dreayrt.fashion_store.Util.HashUtil;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.core.Ordered;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class Security {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtFillter jwtFillter;

    // Custom UTF-8 Encoding Filter để đảm bảo tất cả request đều dùng UTF-8
    public static class Utf8EncodingFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            // Set request encoding to UTF-8 before reading parameters
            if (request.getCharacterEncoding() == null) {
                request.setCharacterEncoding("UTF-8");
            }
            // Also set response encoding
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
        }
    }

    // Đăng ký UTF-8 encoding filter với tên unique để tránh conflict
    @Bean
    public FilterRegistrationBean<Filter> utf8EncodingFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Utf8EncodingFilter());
        registrationBean.setName("utf8EncodingFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Chạy đầu tiên
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/index",
                                "/pages/login", "/pages/register",
                                "/authentication/**",
                                "/css/**", "/js/**", "/images/**", "/Logo/**", "/imageProduct/**", "/Avatar/**"
                        ).permitAll()
                        .requestMatchers("/pages/addProducts", "/pages/updateProducts", "/pages/deleteProducts", "/pages/kho")
                        .hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/pages/profile", "/pages/ShoppingCart", "/pages/logout")
                        .authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/pages/login");
                        })
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityContext(context -> context.securityContextRepository(new NullSecurityContextRepository()))
                .addFilterBefore(jwtFillter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return HashUtil.sha256(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return HashUtil.sha256(rawPassword.toString()).equalsIgnoreCase(encodedPassword);
            }
        };
    }
}
