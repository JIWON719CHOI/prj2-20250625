package com.example.prj2.domain.member.dto;

import com.example.prj2.domain.member.entity.Member;
import com.example.prj2.domain.member.entity.MemberRole;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.prj2.domain.member.entity.Member}
 */
@Value
@NoArgsConstructor(force = true)  // 모든 final 필드를 null(default)로 초기화해주는 빈 생성자
public class MemberSignupDto implements Serializable {
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
        member.setRole(MemberRole.USER); // 🔥 기본값 명시
        return member;
    }

}

