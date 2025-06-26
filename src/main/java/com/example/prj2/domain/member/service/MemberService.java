package com.example.prj2.domain.member.service;

import com.example.prj2.domain.board.repo.BoardRepository;
import com.example.prj2.domain.comment.repo.CommentRepository;
import com.example.prj2.domain.member.dto.MemberProfileDto;
import com.example.prj2.domain.member.dto.PasswordChangeDto;
import com.example.prj2.domain.member.dto.ProfileUpdateDto;
import com.example.prj2.domain.member.dto.SignupDto;
import com.example.prj2.domain.member.entity.Member;
import com.example.prj2.domain.member.exception.DuplicateMemberException;
import com.example.prj2.domain.member.exception.InvalidPasswordException;
import com.example.prj2.domain.member.exception.MemberNotFoundException;
import com.example.prj2.domain.member.repo.MemberRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public void add(@Valid SignupDto dto) {
        if (memberRepository.existsById(dto.getId())) {
            throw new DuplicateMemberException("이미 사용 중인 아이디입니다.");
        }
        if (memberRepository.existsByName(dto.getName())) {
            throw new DuplicateMemberException("이미 사용 중인 이름 입니다.");
        }
        memberRepository.save(dto.toEntity());
    }

    public boolean login(String id, String password, HttpSession session) {
        Optional<Member> memberOpt = memberRepository.findById(id);

        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            if (member.getPassword().equals(password)) {
                // 로그인 성공 시 세션 저장
                session.setAttribute("loginUser", member.getId());
                return true;
            }
        }
        return false;
    }

    public void updateProfile(ProfileUpdateDto dto) {
        Member m = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new MemberNotFoundException("회원이 없습니다."));
        m.setName(dto.getName());
        m.setInfo(dto.getInfo());
    }

    public void changePassword(PasswordChangeDto dto) {
        Member m = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new MemberNotFoundException("회원이 없습니다."));
        if (!m.getPassword().equals(dto.getOldPassword())) {
            throw new InvalidPasswordException("기존 비밀번호가 일치하지 않습니다.");
        }
        m.setPassword(dto.getNewPassword());
    }

    public void delete(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("회원이 없습니다."));

        // 게시글 FK 해제 + 이름 변경
        boardRepository.findByAuthor_Id(memberId)
                .forEach(post -> {
                    post.setAuthor(null);
                    post.setAuthorName("(탈퇴)");
                });

        // 댓글 FK 해제 + 이름 변경
        commentRepository.findByAuthor_Id(memberId)
                .forEach(c -> {
                    c.setAuthor(null);
                    c.setAuthorName("(탈퇴)");
                });

        // 실제 회원 삭제
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public MemberProfileDto get(String id) {
        return memberRepository
                .findProjectedById(id)
                .orElseThrow(() -> new MemberNotFoundException("회원이 없습니다."));
    }
}
