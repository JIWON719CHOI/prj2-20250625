package com.example.prj2.domain.member.dto;

import com.example.prj2.domain.member.entity.Member;
import com.example.prj2.domain.member.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.prj2.domain.member.entity.Member}
 */
@Value
public class SignupDto implements Serializable {
    @NotBlank
    String id;
    @NotBlank
    String password;
    @NotBlank
    String name;

    // ✅ Entity로 변환하는 메서드
    public Member toEntity() {
        Member member = new Member();
        member.setId(this.id);
        member.setPassword(this.password);
        member.setName(this.name);
        member.setRole(Role.USER); // 🔥 기본값 명시
        return member;
    }

}

