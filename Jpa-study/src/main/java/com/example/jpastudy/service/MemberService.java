package com.example.jpastudy.service;


import com.example.jpastudy.entity.Member;
import com.example.jpastudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

//    @Autowired
//    private MemberService memberService;

//    private  MemberRepository getMemberRepository() {
//        return memberRepository;
//    }

    // ── CREATE ──────────────────────────────────
    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }
    // ── READ ────────────────────────────────────
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found: " + id));
    }
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
    // ── UPDATE ──────────────────────────────────
    @Transactional
    public Member update(Long id, String newUsername) {
        Member member = findById(id);
        member.setUsername(newUsername); // 변경 감지로 자동 UPDATE!
        return member;
    }
    // ── DELETE ──────────────────────────────────
    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
    public long count() {
        return memberRepository.count();
    }
}
