package com.example.member.service;

import com.example.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional // 원본 db에는 영향을 주지 않는다....test시 바로 rollback
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void save() {

        Member member = new Member();
        member.setName("신난다");
        member.setEmail("user@ouser.com");

        memberService.save(member);
    }




}