package com.example.prj2.domain.comment.repo;

import com.example.prj2.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByAuthor_Id(String authorId);
}