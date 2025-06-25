package com.example.prj2.global.exception;

import com.example.prj2.domain.member.exception.DuplicateMemberException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateMemberException.class)
    public String handleDuplicateMember(DuplicateMemberException e, RedirectAttributes rttr) {
        rttr.addFlashAttribute("alert", e.getMessage());
        return "redirect:/member/signup";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception e, RedirectAttributes rttr) {
        rttr.addFlashAttribute("alert", "예기치 못한 오류가 발생했습니다.");
        return "redirect:/";
    }
}
