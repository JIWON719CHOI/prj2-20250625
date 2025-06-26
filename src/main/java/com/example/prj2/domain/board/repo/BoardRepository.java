package com.example.prj2.domain.board.repo;

import com.example.prj2.domain.board.dto.*;
import com.example.prj2.domain.board.entity.Board;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    // author.id 프로퍼티로 찾는다
    List<Board> findByAuthor_Id(String authorId);

    // 페이징도 바로 지원
    Page<BoardListDto> findAllProjectedBy(Pageable pageable);

    // id로 조회해서 프로젝션을 반환
    Optional<BoardDetailDto> findProjectedById(Integer id);

    // 페이징 없이 전체 목록 + 프로젝션 + 정렬
    List<BoardDetailDto> findAllProjectedBy(Sort sort);
}