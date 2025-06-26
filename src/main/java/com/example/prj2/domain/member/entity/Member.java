package com.example.prj2.domain.member.entity;

import com.example.prj2.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "member", schema = "prj2")
public class Member extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "info", length = 1000)
    private String info;

    @Enumerated(EnumType.STRING) // DB 에는 "USER", "ADMIN" 문자열로 저장됨
    @Column(name = "role", nullable = false)
    private Role role;

}