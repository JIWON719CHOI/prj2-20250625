package com.example.prj2.global.exception;

import com.example.prj2.domain.member.exception.DuplicateMemberException;
import com.example.prj2.domain.member.exception.MemberException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 가입 중복만 별도 처리
    @ExceptionHandler(DuplicateMemberException.class)
    public String handleDuplicate(DuplicateMemberException e, RedirectAttributes rttr) {
        rttr.addFlashAttribute("alert", e.getMessage());
        return "redirect:/member/signup";
    }

    // 그 외 모든 회원 예외는 한 번에 처리
    @ExceptionHandler(MemberException.class)
    public String handleMemberAll(MemberException e, RedirectAttributes rttr) {
        rttr.addFlashAttribute("alert", e.getMessage());
        return "redirect:/";
    }

    // 그 외 예외
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception e, RedirectAttributes rttr) {
        rttr.addFlashAttribute("alert", "알 수 없는 오류가 발생했습니다.");
        return "redirect:/";
    }
}
