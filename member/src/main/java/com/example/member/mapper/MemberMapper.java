package com.example.member.mapper;

import com.example.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    // 전체 회원 조회
    List<Member> findAll();

    // 이름 검색
    public List<Member> findByNameContaining(@Param("keyword") String keyword);

    // pk로 단건 조회
    public Member findById(@Param("id")Long id);

    // 이메일로 조회(중복 체크)
    public Member findByEmail(@Param("email")String name);

    // 회원 정보 저장
    public int insertMember(Member member);

    // 회원 정보 수정
    public int updateMember(Member member);

    // 회원 정보 삭제
    public int deleteMember(@Param("id")Long id);

//    @Param("이름")은 XML에서 #{이름}으로 사용됨. 파라미타가 1개일 때는 생략 가능하지만,
//    2개 이상이면 반드시 붙여야 함.
}

