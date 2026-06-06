package com.shop.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
// 1. disable()을 삭제하고 기본 CSRF 설정을 유지하도록 바꿉니다.
                // (스프링 부트 3.x/4.x 이상 람다식 기본형태)
                .csrf(csrf -> {})

                .authorizeHttpRequests(auth -> auth
                        // 2. 누구나 접근 가능해야 하는 경로들을 명시해 줍니다.
                        .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build(); // 3. 반드시 http.build()를 리턴해야 합니다!
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
