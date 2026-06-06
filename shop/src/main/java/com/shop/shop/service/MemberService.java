package com.shop.shop.service;

import com.shop.shop.entity.Member;
import com.shop.shop.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static groovyjarjarantlr4.v4.gui.Trees.save;

@Service
@Transactional
@RequiredArgsConstructor

public class MemberService {

//    @Autowired
    private final MemberRepository memberRepository;
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
