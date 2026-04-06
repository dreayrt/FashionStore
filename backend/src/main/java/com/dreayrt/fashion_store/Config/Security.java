package com.dreayrt.fashion_store.Config;

import com.dreayrt.fashion_store.Service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import com.dreayrt.fashion_store.Util.HashUtil;

@Configuration
@EnableWebSecurity
public class Security {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private CustomUserDetailService customUserDetailService;   // 🔥 thêm dòng này

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Trang public
                        .requestMatchers("/pages/login", "/pages/login/**").permitAll()
                        .requestMatchers("/login", "/logout").permitAll()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/img/**", "/imageProduct/**", "/Logo/**").permitAll()
                        // Trang kho chỉ admin hoặc staff
                        .requestMatchers("/pages/kho").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/pages/addProducts").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/pages/deleteProducts").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/pages/updateProducts").hasAnyRole("ADMIN", "STAFF")
                        //Trang can login
                        .requestMatchers("/pages/ShoppingCart").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/pages/login") // trang form
                        .loginProcessingUrl("/login") // POST xử lý
                        .failureUrl("/pages/login?error") // cần slash đầu
                        // Dùng success handler để set sessionScope.user và quay lại trang đã yêu cầu
                        .successHandler(loginSuccessHandler)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/pages/login?logout")
                )
                .rememberMe(r -> r
                        .key("fashion-store-key")
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/pages/failed"))
                .csrf(csrf -> csrf.disable());

        return http.build();


    }
    // 🔥 thêm bean này để bind UserDetailsService vào Security
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
    // PasswordEncoder dùng SHA-256 để khớp với giá trị đang lưu trong DB (HashUtil.sha256)
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
