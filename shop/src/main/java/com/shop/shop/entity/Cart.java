package com.shop.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString

public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    // 일대일로 매핑 member 엔티티와  (fetch = FetchType.EAGER) 즉시로딩
    @OneToOne
    @JoinColumn(name = "member_id")

    private Member member;
}
