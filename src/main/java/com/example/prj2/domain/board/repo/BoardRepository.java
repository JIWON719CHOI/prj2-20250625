package com.example.prj2.domain.board.repo;

import com.example.prj2.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    // author.id 프로퍼티로 찾는다
    List<Board> findByAuthor_Id(String authorId);
}