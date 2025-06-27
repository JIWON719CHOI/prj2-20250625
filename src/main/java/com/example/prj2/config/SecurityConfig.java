package com.example.prj2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // new
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity  // @PreAuthorize 등 메서드 보안 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF는 폼 로그인 + 브라우저 기반이면 켜 두시는 걸 권장합니다.
                // 필요시 .csrf(csrf -> csrf.disable())
                .csrf(AbstractHttpConfigurer::disable)

                // URL 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/board/**").authenticated()
                        .anyRequest().permitAll()
                )

                // 로그인 폼 설정
                .formLogin(form -> form
                        .loginPage("/members/login")              // 로그인 폼 URL
                        .loginProcessingUrl("/members/login")     // 로그인 처리 URL ← 이거 추가!
                        .defaultSuccessUrl("/board")
                        .permitAll()
                )


                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/members/logout")
                        .logoutSuccessUrl("/home")
                );

        return http.build();
    }

    // 비밀번호 암호화를 위한 PasswordEncoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
