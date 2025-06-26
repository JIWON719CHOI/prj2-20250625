package com.example.prj2.domain.admin.controller;

import com.example.prj2.domain.board.dto.BoardDetailDto;
import com.example.prj2.domain.board.service.BoardService;
import com.example.prj2.domain.board.entity.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")   // 스프링 시큐리티로 관리자만 접근 허용
@RequestMapping("/admin/board")
public class AdminBoardController {

    private final BoardService boardService;

    // 전체 게시글 목록 (페이지 없이 한 번에 조회하거나, 검색·필터 기능 추가)
    @GetMapping
    public String listAll(Model model) {
        List<BoardDetailDto> all = boardService.listAll();
        model.addAttribute("allBoards", all);
        return "admin/board/list";
    }

    // 공지글로 변경
    @PostMapping("/{id}/notice")
    public String makeNotice(@PathVariable int id, RedirectAttributes rttr) {
        boardService.changeType(id, BoardType.NOTICE);
        rttr.addFlashAttribute("alert", "게시글을 공지로 설정했습니다.");
        return "redirect:/board/" + id;
    }

    // 비공개 처리
    @PostMapping("/{id}/private")
    public String makePrivate(@PathVariable int id, RedirectAttributes rttr) {
        boardService.changeType(id, BoardType.PRIVATE);
        rttr.addFlashAttribute("alert", "게시글을 비공개로 설정했습니다.");
        return "redirect:/board/" + id;
    }

    // 일반글으로 복원
    @PostMapping("/{id}/general")
    public String makeGeneral(@PathVariable int id, RedirectAttributes rttr) {
        boardService.changeType(id, BoardType.GENERAL);
        rttr.addFlashAttribute("alert", "게시글을 일반글로 전환했습니다.");
        return "redirect:/board/" + id;
    }

    // 게시글 강제 삭제 (작성자 무관)
    @DeleteMapping("/{id}")
    public String deleteAny(
            @PathVariable Integer id,
            RedirectAttributes rttr
    ) {
        boardService.forceDelete(id);
        rttr.addFlashAttribute("alert", "관리자에 의해 게시글이 삭제되었습니다.");
        return "redirect:/admin/board";
    }

    // 타입 변경
    @PostMapping("/{id}/type")
    public String changeType(
            @PathVariable Integer id,
            @RequestParam BoardType type,
            RedirectAttributes rttr
    ) {
        boardService.changeType(id, type);
        rttr.addFlashAttribute("alert", "게시글 타입이 변경되었습니다.");
        return "redirect:/admin/board/" + id;
    }

    // … 기타 신고 관리, 통계, 검색 기능 등
}
