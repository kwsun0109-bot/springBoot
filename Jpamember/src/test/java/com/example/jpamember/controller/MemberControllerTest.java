package com.example.jpamember.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @SneakyThrows
    @Test
    @DisplayName("회원 목록전체 검색 테스트")
    public void list() throws Exception {
        mockMvc.perform(get("/member/list"))
                .andExpect(status().isOk())  // 웹페이지의 상태코드 반환
                .andExpect(view().name("member/list"))
                .andExpect(model().attributeExists("members"));
    }

    @Test
    @DisplayName("회원 검색 테스트")
    public void search() throws Exception {
        mockMvc.perform(get("/member/list").param("keyword","신"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/list"))
                .andExpect(model().attributeExists("members"));
    }

    @Test
    @DisplayName("회원 등록 테스트")
    public void create() throws Exception {
        mockMvc.perform(post("/member/new")
                .param("name", "재밌다")
                .param("email", "woale@examp.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/list"))
                .andExpect(flash().attributeExists("message"));
    }
}

