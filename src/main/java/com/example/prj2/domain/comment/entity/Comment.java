package com.example.prj2.domain.comment.entity;

import com.example.prj2.global.entity.BaseEntity;
import com.example.prj2.domain.board.entity.Board;
import com.example.prj2.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "comment", schema = "prj2")  // ← 스키마 이름 수정
public class Comment extends BaseEntity {    // ← 클래스명은 대문자로 시작

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ← AUTO_INCREMENT 대응
    private Integer id;                                   // ← INT 타입

    @Column(nullable = false, length = 1000)
    private String content;

    // 게시글과 다대일 관계
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    // 회원과 다대일 관계
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    @Column(name = "author_name", nullable = false, length = 255)
    private String authorName;
}
