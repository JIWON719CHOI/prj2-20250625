package com.example.prj2.domain.board.controller;

import com.example.prj2.domain.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/write")
    public String writeForm(HttpSession session, RedirectAttributes rttr) {
        if (session.getAttribute("loggedInUser") == null) {
            rttr.addFlashAttribute("alert", Map.of("code", "warning", "message", "로그인 후 글을 작성해주세요."));
            return "redirect:/member/login";
        }
        return "board/write";
    }

    @PostMapping("/write")
}
