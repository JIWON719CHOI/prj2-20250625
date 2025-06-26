package com.example.prj2.domain.board.service;

import com.example.prj2.domain.board.repo.BoardRepository;
import com.example.prj2.domain.comment.repo.CommentRepository;
import com.example.prj2.domain.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


}
