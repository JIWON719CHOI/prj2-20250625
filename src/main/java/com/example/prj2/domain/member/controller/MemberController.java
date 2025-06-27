package com.example.prj2.domain.member.controller;

import com.example.prj2.domain.member.dto.*;
import com.example.prj2.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")        // 복수형 리소스
public class MemberController {

    private final MemberService memberService;

    // ─────────────────────────────────────────────────
    // 1) 회원 가입
    // ─────────────────────────────────────────────────

    // 가입 폼
    @GetMapping("/new")
    public String newMemberForm(Model model) {
        model.addAttribute("signupDto", new MemberSignupDto());
        return "members/signup";    // templates/member/signup.html
    }

    // 가입 처리
    @PostMapping
    public String createMember(
            @Valid @ModelAttribute("signupDto") MemberSignupDto dto,
            BindingResult br,
            Model model,               // ← RedirectAttributes 대신 Model
            RedirectAttributes rttr) {

        if (br.hasErrors()) {
            model.addAttribute("signupDto", dto);
            return "members/signup";  // ← redirect 아님, 바로 뷰 리턴
        }

        memberService.add(dto);
        rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "가입 완료"));
        return "redirect:/members/" + dto.getId() + "/edit";
    }


    // ─────────────────────────────────────────────────
    // 2) 로그인 / 로그아웃 (인증은 일반적으로 별도 컨트롤러로 분리하기도 합니다)
    // ─────────────────────────────────────────────────

    @GetMapping("/login")
    public String loginForm(Model model) {
        if (!model.containsAttribute("loginDto")) {
            model.addAttribute("loginDto", new MemberLoginDto());
        }
        return "members/login";
    }

    @PostMapping("/login")
    public String loginProcess(
            @Valid @ModelAttribute("loginDto") MemberLoginDto dto,
            BindingResult br,
            HttpSession session,
            Model model
    ) {
        if (br.hasErrors()) {
            // 바인딩 오류가 있을 경우, 그대로 뷰 리턴 (redirect ❌)
            return "members/login";
        }

        boolean ok = memberService.login(dto.getId(), dto.getPassword(), session);
        if (ok) {
            return "redirect:/home";
        } else {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "members/login";
        }
    }


    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        session.invalidate();
        rttr.addFlashAttribute("alert", "로그아웃 되었습니다.");
        return "redirect:/home";
    }

    // ─────────────────────────────────────────────────
    // 3) 회원 조회 / 수정 / 삭제
    // ─────────────────────────────────────────────────

    // (a) 상세 조회
    @GetMapping("/{id}")
    public String showMember(@PathVariable String id, Model model) {
        model.addAttribute("member", memberService.get(id));
        return "members/profile";   // templates/member/profile.html
    }

    // (b) 프로필 수정 폼
    @GetMapping("/{id}/edit")
    public String editProfileForm(@PathVariable String id, Model model) {
        MemberProfileDto profile = memberService.get(id);
        MemberProfileUpdateDto form = new MemberProfileUpdateDto();
        form.setId(profile.getId());
        form.setName(profile.getName());
        form.setInfo(profile.getInfo());
        model.addAttribute("form", form);
        return "members/edit-profile";  // templates/member/edit-profile.html
    }

    // (c) 프로필 수정 처리
    @PutMapping("/{id}")
    public String updateProfile(
            @PathVariable String id,
            @Valid @ModelAttribute("form") MemberProfileUpdateDto dto,
            BindingResult br,
            RedirectAttributes rttr) {

        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            return "redirect:/members/" + id + "/edit";
        }

        memberService.updateProfile(dto);
        rttr.addFlashAttribute("alert", Map.of(
                "code", "success",
                "message", "프로필이 수정되었습니다."
        ));
        return "redirect:/members/" + id;
    }

    // (d) 비밀번호 수정 폼
    @GetMapping("/{id}/password/edit")
    public String changePasswordForm(@PathVariable String id, Model model) {
        MemberPasswordChangeDto form = new MemberPasswordChangeDto();
        form.setId(id);
        model.addAttribute("form", form);
        return "members/change-password";  // templates/member/change-password.html
    }

    // (e) 비밀번호 변경 처리
    @PutMapping("/{id}/password")
    public String changePassword(
            @PathVariable String id,
            @Valid @ModelAttribute("form") MemberPasswordChangeDto dto,
            BindingResult br,
            RedirectAttributes rttr) {

        if (br.hasErrors()) {
            rttr.addFlashAttribute("errors", br.getAllErrors());
            return "redirect:/members/" + id + "/password/edit";
        }

        memberService.changePassword(dto);
        rttr.addFlashAttribute("alert", Map.of(
                "code", "success",
                "message", "비밀번호가 변경되었습니다."
        ));
        return "redirect:/members/" + id;
    }

    // (f) 회원 탈퇴 처리
    @DeleteMapping("/{id}")
    public String deleteMember(
            @PathVariable String id,
            HttpSession session,
            RedirectAttributes rttr) {

        memberService.delete(id);
        session.invalidate();
        rttr.addFlashAttribute("alert", Map.of(
                "code", "success",
                "message", "정상적으로 탈퇴 처리되었습니다."
        ));
        return "redirect:/home";
    }

}
