package com.example.prj2.domain.board.service;

import com.example.prj2.domain.board.dto.*;
import com.example.prj2.domain.board.entity.BoardType;
import com.example.prj2.domain.board.exception.*;
import com.example.prj2.domain.board.entity.Board;
import com.example.prj2.domain.board.repo.BoardRepository;
import com.example.prj2.domain.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    // 목록 조회 (페이징 + 최근순)
    @Transactional(readOnly = true)
    public Page<BoardListDto> list(int page) {
        return boardRepository.findAllProjectedBy(
                PageRequest.of(page - 1, 10,
                        Sort.by("createdAt").descending())
        );
    }

    // 새 글 작성
    public int create(BoardCreateDto dto, String authorId) {
        Board b = new Board();
        b.setTitle(dto.getTitle());
        b.setContent(dto.getContent());
        b.setAuthor(memberRepository.getById(authorId));
        b.setAuthorName(b.getAuthor().getName());
        // dto.getType()이 null 이면 GENERAL
        b.setType(dto.getType() != null ? dto.getType() : BoardType.GENERAL);
        boardRepository.save(b);
        return b.getId();
    }

    // 상세 조회(읽기 전용)
    @Transactional(readOnly = true)
    public BoardDetailDto get(int id) {
        return boardRepository
                .findProjectedById(id)
                .orElseThrow(() -> new BoardNotFoundException(id + "번 게시물이 존재하지 않습니다."));
    }

    // 수정 폼 & 권한 체크
    @Transactional(readOnly = true)
    public BoardDetailDto getForEdit(Integer id, String loginUserId) {
        BoardDetailDto dto = boardRepository
                .findProjectedById(id)
                .orElseThrow(() -> new BoardNotFoundException(id + "번 게시물이 없습니다."));

        // authorId 정보가 DTO에 없기 때문에, 추가로 엔티티 한번 더 로딩하거나
        // repo 에 authorId 프로젝션 메서드를 하나 더 만들 수도 있습니다.
        // 예시에서는 간단히 엔티티 로딩:
        Board b = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id + "번 게시물이 없습니다."));
        if (!b.getAuthor().getId().equals(loginUserId)) {
            throw new UnauthorizedBoardException("게시글 수정 권한이 없습니다.");
        }

        return dto;
    }

    // 글 수정
    public void update(Integer id, BoardUpdateDto data, String loginUserId) {
        Board b = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id + "번 게시물이 없습니다."));
        if (!b.getAuthor().getId().equals(loginUserId)) {
            throw new UnauthorizedBoardException("게시글 수정 권한이 없습니다.");
        }
        b.setTitle(data.getTitle());
        b.setContent(data.getContent());
        b.setType(data.getType());
    }

    // 글 삭제 (작성자만)
    public void delete(int id, String userId) {
        Board b = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id + "번 게시물이 존재하지 않습니다."));
        if (!b.getAuthor().getId().equals(userId)) {
            throw new UnauthorizedBoardException("수정 권한이 없습니다.");
        }
        boardRepository.delete(b);
    }

    // (관리자) 타입 변경
    public void changeType(int boardId, BoardType newType) {
        Board b = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 없습니다."));
        b.setType(newType);
    }

    // (관리자) 전체 글 조회 (페이징 없이 한 번에 혹은 별도 페이징)
    @Transactional(readOnly = true)
    public List<BoardDetailDto> listAll() {
        return boardRepository.findAllProjectedBy(Sort.by("createdAt").descending());
    }

    // (관리자) 강제 삭제: 작성자 무시
    public void forceDelete(int id) {
        Board b = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id + "번 게시물이 없습니다."));
        boardRepository.delete(b);
    }
}
