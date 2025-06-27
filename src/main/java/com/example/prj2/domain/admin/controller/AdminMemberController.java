package com.example.prj2.domain.admin.controller;

import com.example.prj2.domain.member.dto.MemberSimpleDto;
import com.example.prj2.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/members")
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping
    public String listAll(Model model) {
        List<MemberSimpleDto> members = memberService.getAllSimple();
        model.addAttribute("members", members);
        model.addAttribute("current", "adminMemberList"); // navbar 표시용
        return "admin/members/list";
    }
}
