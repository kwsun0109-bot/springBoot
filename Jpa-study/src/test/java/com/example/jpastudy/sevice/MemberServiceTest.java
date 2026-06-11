package com.example.jpastudy.sevice;

import com.example.jpastudy.entity.Member;
import com.example.jpastudy.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional // 테스트 종료 후 롤백
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Test
    void crudTest() {
        // 1. 저장
        Member m = memberService.save(new Member("홍길동", "hong@test.com", 30));
        assertThat(m.getId()).isNotNull();
        System.out.println("저장된 ID: " + m.getId());
        // 2. 조회
        Member found = memberService.findById(m.getId());
        assertThat(found.getUsername()).isEqualTo("홍길동");
        // 3. 수정
        memberService.update(m.getId(), "김철수");
        assertThat(memberService.findById(m.getId()).getUsername()).isEqualTo("김철수");
        // 4. 목록 조회
        List<Member> list = memberService.findAll();
        assertThat(list.size()).isGreaterThan(0);
        // 5. 삭제
        memberService.delete(m.getId());
        assertThat(memberService.count()).isEqualTo(0);
        System.out.println("✅ CRUD 테스트 완료!");
    }
}
