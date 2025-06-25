package com.example.prj2.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.prj2.domain.member.entity.Member}
 */
@Value
public class MemberDetailDto implements Serializable {
    LocalDateTime createdAt;
    @NotBlank
    String id;
    @NotBlank
    String name;
    String info;
}