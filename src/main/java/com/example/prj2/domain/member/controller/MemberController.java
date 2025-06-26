package com.example.prj2.domain.member.controller;

import com.example.prj2.domain.member.dto.*;
import com.example.prj2.domain.member.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

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
    public String signup(
            @Valid @ModelAttribute("signupDto") SignupDto dto,
            BindingResult br,
            RedirectAttributes rttr) {

        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            rttr.addFlashAttribute("alert", Map.of("code", "danger", "message", "필수 항목을 모두 입력해주세요"));
            return "redirect:/member/signup";
        }

        memberService.add(dto); // 🚀 서비스가 모든 로직을 책임집니다

        rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "회원 가입되었습니다."));
        return "redirect:/member/edit-profile?id=" + dto.getId();
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/member/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto dto,
                        BindingResult br, HttpSession session,
                        RedirectAttributes rttr) {

        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            rttr.addFlashAttribute("alert", Map.of("code", "danger", "message", "아이디와 비밀번호를 입력해주세요"));
            return "redirect:/member/login";
        }

        boolean success = memberService.login(dto.getId(), dto.getPassword(), session);

        if (success) {
            rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "로그인 되었습니다."));
            return "redirect:/home";
        } else {
            rttr.addFlashAttribute("alert", Map.of("code", "warning", "message", "아이디/비밀번호가 일치하지 않습니다."));
            return "redirect:/member/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        session.invalidate(); // 세션 무효화
        rttr.addFlashAttribute("alert", "로그아웃 되었습니다.");
        return "redirect:/home";
    }

    @GetMapping("/profile")
    public String profile(@RequestParam String id, Model model) {
        model.addAttribute("member", memberService.get(id));
        return "/member/profile";
    }

    @GetMapping("/edit-profile")
    public String editProfileForm(@RequestParam String id, Model model) {
        MemberProfileDto profile = memberService.get(id);

        ProfileUpdateDto form = new ProfileUpdateDto();
        form.setId(profile.getId());
        form.setName(profile.getName());
        form.setInfo(profile.getInfo());

        model.addAttribute("form", form);
        return "member/edit-profile";  // /templates/member/edit-profile.html
    }

    @PostMapping("/edit-profile")
    public String editProfile(
            @Valid @ModelAttribute("form") ProfileUpdateDto dto,
            BindingResult br,
            RedirectAttributes rttr) {

        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            return "redirect:/member/edit-profile?id=" + dto.getId();
        }

        memberService.updateProfile(dto);
        rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "프로필이 수정되었습니다."));
        return "redirect:/member/profile?id=" + dto.getId();
    }

    @GetMapping("/change-password")
    public String changePasswordForm(@RequestParam String id, Model model) {
        PasswordChangeDto form = new PasswordChangeDto();
        form.setId(id);
        model.addAttribute("form", form);
        return "member/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @Valid @ModelAttribute("form") PasswordChangeDto dto,
            BindingResult br,
            RedirectAttributes rttr) {

        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            return "redirect:/member/change-password?id=" + dto.getId();
        }

        memberService.changePassword(dto);
        rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "비밀번호가 변경되었습니다."));
        return "redirect:/member/profile?id=" + dto.getId();
    }

    // 탈퇴 확인 페이지(혹은 모달)
    @GetMapping("/delete-confirm")
    public String deleteConfirm() {
        return "member/delete-confirm"; // 확인 버튼만 있는 간단한 뷰
    }

    // 실제 탈퇴 처리
    @PostMapping("/delete")
    public String deleteAccount(HttpSession session, RedirectAttributes rttr) {
        // 로그인중인 사용자의 id 가져오기
        String id = (String) session.getAttribute("loginUser");
        memberService.delete(id);
        session.invalidate();  // 세션 무효화

        rttr.addFlashAttribute("alert",
                Map.of("code", "success", "message", "정상적으로 탈퇴 처리되었습니다."));
        return "redirect:/home";
    }

}
