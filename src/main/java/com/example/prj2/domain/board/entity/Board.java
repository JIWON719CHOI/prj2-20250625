package com.example.prj2.domain.board.entity;

import com.example.prj2.domain.member.entity.Member;
import com.example.prj2.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "board", schema = "prj2")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    // ↓ 여기만 바꿔주면 됩니다!
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BoardType type;
}
