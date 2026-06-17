package com.shop.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 개발 단계에서는 CSRF 보안을 임시로 비활성화하여 999/403 에러를 방지합니다.
                .csrf(csrf -> csrf.disable())

                // 2. URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // 3. 로그인 설정 (연결 흐름 매끄럽게 세미콜론 제거)
                .formLogin(form -> form
                        .loginPage("/members/login")             // 커스텀 로그인 페이지 경로
                        .defaultSuccessUrl("/")                  // 로그인 성공 시 이동할 경로
                        .usernameParameter("email")              // ID 파라미터명을 email로 변경
                        .failureUrl("/members/login/error")      // 로그인 실패 시 이동할 경로
                )

                // 4. 로그아웃 설정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 URL 매칭
                        .logoutSuccessUrl("/")                   // 로그아웃 성공 시 이동할 경로
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/error");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
