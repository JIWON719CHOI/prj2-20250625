package com.example.prj2.domain.member.repo;

import com.example.prj2.domain.member.dto.MemberProfileDto;
import com.example.prj2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByName(String name);

    // ★ 이 메서드 하나로, 자동으로 DTO 프로젝션 구현체를 반환
    Optional<MemberProfileDto> findProjectedById(String id);
}