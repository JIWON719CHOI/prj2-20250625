package com.example.prj2.domain.comment.service;

import com.example.prj2.domain.comment.dto.CommentCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    public void create(Integer boardId, String loginUserId, @Valid CommentCreateDto dto) {
    }
}
