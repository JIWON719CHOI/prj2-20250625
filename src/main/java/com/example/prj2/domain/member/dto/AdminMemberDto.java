package com.example.prj2.domain.member.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.prj2.domain.member.entity.Member}
 */
@Value
public class AdminMemberDto implements Serializable {
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String id;
    String name;
    String info;
    String role;
}