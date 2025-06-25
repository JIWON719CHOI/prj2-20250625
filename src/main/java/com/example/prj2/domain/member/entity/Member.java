package com.example.prj2.domain.member.entity;

import com.example.prj2.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

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

    @ColumnDefault("'USER'")
    @Lob
    @Column(name = "role", nullable = false)
    private String role;

}