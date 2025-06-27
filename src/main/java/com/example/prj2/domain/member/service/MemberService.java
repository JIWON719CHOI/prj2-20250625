package com.example.prj2.domain.member.service;

import com.example.prj2.domain.member.dto.*;
import com.example.prj2.domain.member.exception.*;
import com.example.prj2.domain.member.entity.Member;
import com.example.prj2.domain.member.repo.MemberRepository;
import com.example.prj2.domain.board.repo.BoardRepository;
import com.example.prj2.domain.comment.repo.CommentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional                // 쓰기 트랜잭션 기본
public class MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 1) 회원 가입
    public void add(MemberSignupDto dto) {
        if (memberRepository.existsById(dto.getId())) {
            throw new DuplicateMemberException("이미 사용 중인 아이디입니다.");
        }
        if (memberRepository.existsByName(dto.getName())) {
            throw new DuplicateMemberException("이미 사용 중인 이름입니다.");
        }
        memberRepository.save(dto.toEntity());
    }

    // 2) 로그인 (세션 처리)
    public boolean login(String id, String password, HttpSession session) {
        Optional<Member> opt = memberRepository.findById(id);
        if (opt.isPresent() && opt.get().getPassword().equals(password)) {
            session.setAttribute("loginUser", id);
            return true;
        }
        return false;
    }

    // 3) 회원 상세 조회 (읽기 전용 최적화)
    @Transactional(readOnly = true)
    public MemberProfileDto get(String id) {
        return memberRepository.findProjectedById(id)
                .orElseThrow(() -> new MemberNotFoundException("회원이 없습니다."));
    }

    // 4) 프로필 수정
    public void updateProfile(MemberProfileUpdateDto dto) {
        Member m = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new MemberNotFoundException("회원이 없습니다."));
        m.setName(dto.getName());
        m.setInfo(dto.getInfo());
    }

    // 5) 비밀번호 변경
    public void changePassword(MemberPasswordChangeDto dto) {
        Member m = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new MemberNotFoundException("회원이 없습니다."));
        if (!m.getPassword().equals(dto.getOldPassword())) {
            throw new InvalidPasswordException("기존 비밀번호가 일치하지 않습니다.");
        }
        m.setPassword(dto.getNewPassword());
    }

    // 6) 회원 탈퇴
    public void delete(String memberId) {
        Member m = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("회원이 없습니다."));

        // 게시글, 댓글 author 관계 해제 및 이름 변경
        boardRepository.findByAuthor_Id(memberId).forEach(post -> {
            post.setAuthor(null);
            post.setAuthorName("(탈퇴)");
        });
        commentRepository.findByAuthor_Id(memberId).forEach(c -> {
            c.setAuthor(null);
            c.setAuthorName("(탈퇴)");
        });

        memberRepository.delete(m);
    }

    // 7) 회원 리스트 - 관리자 전용
    public List<MemberSimpleDto> getAllSimple() {
        return memberRepository.findAll()
                .stream()
                .map(MemberSimpleDto::fromEntity)
                .toList();
    }
}
