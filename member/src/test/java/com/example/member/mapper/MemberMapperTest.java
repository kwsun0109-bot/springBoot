package com.example.member.mapper;

import com.example.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest                   // 전체 스프링 컨텍스트 + 실제 MySql연결
@Transactional                    // 각 테스트 후 자동 롤벡 -> 데이터베이스에 영향 없음

class MemberMapperTest {
    @Autowired
    private  MemberMapper memberMapper;

    // ─── 테스트 1: 전체 조회 ──────────────────────────
    @Test
    void findAllTest() {
        List<Member> memberList = memberMapper.findAll();

        assertThat(memberList).isNotEmpty();
        log.info("===== findAll 결과 =====");
        memberList.forEach(System.out::println);
//        for (Member member : memberList) {
//            log.info("Member : {}", member);
//        }
    }

    // ─── 테스트 2: 등록 + 단건 조회 ──────────────────
    @Test
    void findById() {
//        long id = 2;
//        Member member = memberMapper.findById(id);
//        log.info("Member : {}", member);
        // given
        Member member = new Member();
        member.setName("test");
        member.setEmail("test@test.com");
        // when
        memberMapper.insertMember(member);
        //useGeneratedKeys -> member.gerId() 에 auto_increment 값이 세팅됨
        Member found = memberMapper.findById(member.getId());
        // then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("test");
        assertThat(found.getEmail()).isEqualTo("test@test.com");
        System.out.println("등록된 회원 : " + found.getName());
    }
    // ─── 테스트 3: 수정 ────────────────────────────────
//    @Test
//    void findByNameContaining() {
//        String keyword = "김";
//        memberMapper.findByNameContaining(keyword)
//                .forEach (member -> log.info("Member : {}", member));
//    }

    @Test
    @DisplayName("update - 이름/이메일 수정 후 변경값이 반영되어야 한다")
    @Transactional
    void updateMember() {
//        Member member = new Member(1L, "김현수", "kim@test.com", "010-2222-8888", "서울시 등촌동 어느시골골목");
//        int result = memberMapper.updateMember(member);
//        log.info("result : {}", result);

        // given - 회원 등록
        Member member = new Member();
        member.setName("수정전이름");
        member.setEmail("before3@test.com");
        memberMapper.insertMember(member);
        // when - 이름·이메일 변경 후 update
        member.setName("수정후이름");
        member.setEmail("after3@test.com");
        memberMapper.updateMember(member);
        // then
        Member updated = memberMapper.findById(member.getId());
        assertThat(updated.getName()).isEqualTo("수정후이름");
        assertThat(updated.getEmail()).isEqualTo("after3@test.com");
    }
    // ─── 테스트 4: 삭제 ────────────────────────────────
    @Test
    @DisplayName("deleteById -  삭제 후 findById 결과가 null 이어야 한다")
    void deleteMember() {
//        long id = 81;
//        log.info("Member : {}", memberMapper.deleteMember(id));
        // given
        Member member = new Member();
        member.setName("삭제대상");
        member.setEmail("delete@test.com");
        memberMapper.insertMember(member);
        // when
        memberMapper.deleteMember(member.getId());
        // then
        Member deleted = memberMapper.findById(member.getId());
        assertThat(deleted).isNull();
    }

//    @Test
//    void insertMember() {
//        Member member = new Member();
//        member.setName("임찬규");
//        member.setEmail("Leem@test.com");
//        member.setPhone("010-8888-5487");
//        member.setAddress("충청북도 청주시 막다른 골목에서");
//
//        int result = memberMapper.insertMember(member);
//        log.info("result: {}", result);
//    }
    // ─── 테스트 5: 이름 검색 ──────────────────────────
    @Test
    @DisplayName("findByNameContaining - 키워드가 포함된 회원만 반환되어야 한다")
    void findByNameContaining() {
        // given - '홍' 이라는 이름의 회원등록
        Member member1 = new Member();
        member1.setName("홍길동");
        member1.setAge(55);
        member1.setEmail("test@testtest.com");
        member1.setPhone("010-1234-5678");
        member1.setAddress("경기도 제주도 경상도");

        Member member2 = new Member();
        member2.setName("김홍도");
        member2.setAge(25);
        member2.setEmail("test1@testtest.com");
        member2.setPhone("010-3333-5678");
        member2.setAddress("경기도 전라도 경상도");

        // when
        List<Member> result = memberMapper.findByNameContaining("김");
        // then
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(m -> m.getName().contains("김"));
    }

    @Test
    void findByEmail() {
        String email = "abc@test.com";
        String result = memberMapper.findByEmail("email") == null ? "사용가능" : "이메일 중복";
        log.info("result : {}", result);
    }


}