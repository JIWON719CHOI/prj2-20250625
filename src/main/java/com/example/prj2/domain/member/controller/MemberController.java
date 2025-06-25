package com.example.prj2.domain.member.controller;

import com.example.prj2.domain.member.dto.LoginDto;
import com.example.prj2.domain.member.dto.SignupDto;
import com.example.prj2.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signupForm() {
        return "/member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "/member/signup"; // 입력 값이 비어서 입력 좀 하라고.
        }

        memberService.signup(dto); // 예외 발생 시 ControllerAdvice로 감
        return "redirect:/member/login"; // 성공했어.
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/member/login";
    }

    @PostMapping("login")
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/member/login";
        }
        return "redirect:/home"; // 로그인 성공하면 홈으로?
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        session.invalidate(); // 세션 무효화
        rttr.addFlashAttribute("alert", "로그아웃 되었습니다.");
        return "redirect:/home";
    }

}
