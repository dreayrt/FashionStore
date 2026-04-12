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

@Configuration
@EnableWebSecurity
public class Security {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtFillter jwtFillter;

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
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityContext(context -> context.securityContextRepository(new NullSecurityContextRepository()))
                .addFilterBefore(jwtFillter, UsernamePasswordAuthenticationFilter.class)
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
