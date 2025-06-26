package com.example.prj2.domain.member.dto;

import java.time.LocalDateTime;

// **클래스가 아니라 인터페이스**로 작성**
public interface MemberProfileDto {
    String getId();

    String getName();

    String getInfo();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
