package com.shop.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig { // 1. extends WebSecurityConfigurerAdapter 과감히 삭제!

    // 2. void 리턴 대신 SecurityFilterChain을 반환하는 @Bean 메서드로 변경
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> {})

                // 1. URL별 권한 설정
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                );

                // 2. 로그인 설정 (람다식 적용)
                http.formLogin(form -> form
                        .loginPage("/members/login")             // 커스텀 로그인 페이지 경로
                        .defaultSuccessUrl("/")                  // 로그인 성공 시 이동할 경로
                        .usernameParameter("email")              // 로그인 시 ID로 사용할 파라미터 이름 (기본값 username -> email로 변경)
                        .failureUrl("/members/login/error")      // 로그인 실패 시 이동할 경로
                );

                // 3. 로그아웃 설정 (람다식 적용 및 괄호 마감 해결)
                http.logout(logout -> logout
                        .logoutUrl("/members/logout") // 로그아웃 요청 경로
                        .logoutSuccessUrl("/")                   // 로그아웃 성공 시 이동할 경로
                );

        return http.build(); // 3. 반드시 http.build()를 리턴해야 합니다!
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
