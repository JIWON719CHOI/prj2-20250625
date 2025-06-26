package com.example.prj2.domain.board.dto;

import java.time.LocalDateTime;

public interface BoardListDto {
    Integer getId();

    String getTitle();

    String getContent();

    String getAuthorName();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    // 편의 메서드는 default 메서드로
    default LocalDateTime getDisplayTime() {
        return getUpdatedAt() != null ? getUpdatedAt() : getCreatedAt();
    }
}