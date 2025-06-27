package com.example.prj2.domain.comment.controller;

import com.example.prj2.domain.comment.dto.CommentCreateDto;
import com.example.prj2.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/board/{boardId}/comments")
    public String create(
            @PathVariable Integer boardId,
            @Valid @ModelAttribute("commentDto") CommentCreateDto cdto,
            BindingResult br,
            @SessionAttribute("loginUser") String loginUserId,
            RedirectAttributes rttr) {
        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            return "redirect:/board/" + boardId;
            
        }
        commentService.create(boardId, loginUserId, cdto);
        return "redirect:/board/" + boardId;
    }

}
