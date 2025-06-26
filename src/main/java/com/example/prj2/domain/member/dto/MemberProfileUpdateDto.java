package com.example.prj2.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberProfileUpdateDto {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String info;
}
