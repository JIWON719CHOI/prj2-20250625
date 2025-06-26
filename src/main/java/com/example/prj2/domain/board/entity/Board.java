package com.example.prj2.domain.board.entity;

import com.example.prj2.domain.member.entity.Member;
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
@Table(name = "board", schema = "prj2")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // ↓ 여기만 바꿔주면 됩니다!
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BoardType type;
}
