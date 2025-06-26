package com.example.prj2.domain.board.dto;

import com.example.prj2.domain.board.entity.BoardType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardUpdateDto {
    private Integer id;
    private String title;
    private String content;
    @Builder.Default
    private BoardType type = BoardType.GENERAL;
}