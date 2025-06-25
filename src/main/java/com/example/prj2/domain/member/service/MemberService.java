package com.example.prj2.domain.member.service;

import com.example.prj2.domain.member.dto.SignupDto;
import com.example.prj2.domain.member.exception.DuplicateMemberException;
import com.example.prj2.domain.member.repo.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void signup(@Valid SignupDto dto) {
        if (memberRepository.existsById(dto.getId())) {
            throw new DuplicateMemberException("이미 사용 중인 아이디입니다.");
        }
    }
}
