package com.shop.shop.entity;

import com.shop.shop.constant.Role;
import com.shop.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)   // 회원 이메일로 동일한 값이 데이터베이스로 들오올 수 없도록 unique속성을 지정
    private String email;

    private String password;
    private String address;

    @Enumerated(EnumType.STRING)  // 기본순서가 변경시 문제가 발생할 수 있으므로 'Enumtype.STRING' 옵션을 사용해 저장(권장)

    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }

}
