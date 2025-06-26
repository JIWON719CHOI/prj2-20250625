package com.example.prj2.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeDto {
    @NotBlank
    private String id;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
