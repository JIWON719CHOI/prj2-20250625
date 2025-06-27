package com.example.prj2.domain.member.dto;

import com.example.prj2.domain.member.entity.Member;
import com.example.prj2.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberSimpleDto {
    private String id;
    private String name;
    private String info;
    private MemberRole role;

    public static MemberSimpleDto fromEntity(Member member) {
        return new MemberSimpleDto(
                member.getId(),
                member.getName(),
                member.getInfo(),
                member.getRole()
        );
    }
}
