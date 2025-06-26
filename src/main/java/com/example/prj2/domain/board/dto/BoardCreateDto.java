package com.example.prj2.domain.board.dto;

import com.example.prj2.domain.board.entity.BoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCreateDto {
    @NotBlank
    String title;
    @NotBlank
    String content;
    @NotNull
    @Builder.Default
    private BoardType type = BoardType.GENERAL;  // ← 빌더 사용 시에도 기본값
}
