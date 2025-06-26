package com.example.prj2.domain.board.dto;

import com.example.prj2.domain.board.entity.BoardType;

import java.time.LocalDateTime;

public interface BoardDetailDto {

    Integer getId();

    String getTitle();

    String getContent();

    BoardType getType();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    // 편의 메서드는 default 메서드로
    default LocalDateTime getDisplayTime() {
        return getUpdatedAt() != null ? getUpdatedAt() : getCreatedAt();
    }
}
