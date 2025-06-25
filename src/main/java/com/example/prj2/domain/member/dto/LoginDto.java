package com.example.prj2.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.prj2.domain.member.entity.Member}
 */
@Value
public class LoginDto implements Serializable {
    @NotBlank
    String id;
    @NotBlank
    String password;
}