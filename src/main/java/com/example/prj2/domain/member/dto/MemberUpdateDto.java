package com.example.prj2.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for {@link com.example.prj2.domain.member.entity.Member}
 */
@Data
public class MemberUpdateDto {
    @NotBlank
    String id;
    @NotBlank
    String name;
    @NotBlank
    String password;
    @NotBlank
    String oldPassword;
    @NotBlank
    String newPassword;
    String info;
}
