package com.example.prj2.domain.member.repo;

import com.example.prj2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}