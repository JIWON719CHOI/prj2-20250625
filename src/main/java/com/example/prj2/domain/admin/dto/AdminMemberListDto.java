package com.example.prj2.domain.admin.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.prj2.domain.member.entity.Member}
 */
@Value
public class AdminMemberListDto implements Serializable {
    String id;
    String name;
    String role;
    int reportCount;           // ✅ 신고 횟수
    LocalDateTime createdAt;   // 가입일
}
