package com.example.prj2.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.example.prj2.domain.member.entity.Member}
 */
@Data
public class MemberLoginDto implements Serializable {
    @NotBlank
    String id;
    @NotBlank
    String password;
}