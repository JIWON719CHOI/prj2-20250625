package com.example.prj2.domain.board.controller;

import com.example.prj2.domain.board.dto.*;
import com.example.prj2.domain.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 1) 목록 조회
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        Page<BoardListDto> pageData = boardService.list(page);
        model.addAttribute("pageData", pageData);
        return "board/list";
    }

    // 2) 생성 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createDto", new BoardCreateDto());
        return "board/new";
    }

    // 3) 게시글 생성
    @PostMapping
    public String create(
            @Valid @ModelAttribute("createDto") BoardCreateDto dto,
            BindingResult br,
            HttpSession session,
            RedirectAttributes rttr
    ) {
        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            return "redirect:/board/new";
        }
        String authorId = (String) session.getAttribute("loginUser");
        int id = boardService.create(dto, authorId);
        return "redirect:/board/" + id;
    }

    // 4) 상세 조회
    @GetMapping("/{id}")
    public String detail(
            @PathVariable int id,
            Model model
    ) {
        BoardDetailDto board = boardService.get(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    // 5) 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable Integer id,
            Model model,
            @SessionAttribute("loginUser") String loginUserId
    ) {
        // 인터페이스 프로젝션 그대로 전달
        BoardDetailDto dto = boardService.getForEdit(id, loginUserId);
        model.addAttribute("board", dto);
        return "board/edit";
    }

    // 6) 게시글 수정
    @PutMapping("/{id}")
    public String update(
            @PathVariable Integer id,
            @Valid @ModelAttribute("updateDto") BoardUpdateDto data,
            BindingResult br,
            @SessionAttribute("loginUser") String loginUserId,
            RedirectAttributes rttr
    ) {
        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            return "redirect:/board/" + id + "/edit";
        }
        boardService.update(id, data, loginUserId);
        rttr.addFlashAttribute("alert", Map.of(
                "code", "success",
                "message", id + "번 게시물이 수정되었습니다."
        ));
        return "redirect:/board/" + id;
    }

    // 7) 삭제
    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable int id,
            @SessionAttribute("loginUser") String loginUserId,  // 로그인 사용자
            RedirectAttributes rttr
    ) {
        boardService.delete(id, loginUserId);
        rttr.addFlashAttribute("alert", "게시물이 삭제되었습니다.");
        return "redirect:/board";
    }
}

