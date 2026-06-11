package com.example.jpamember.controller;

import com.example.jpamember.entity.JpaMember;
import com.example.jpamember.service.MemberService;
import com.zaxxer.hikari.util.FastList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    // ─── 목록 조회 + 검색 ───────────────────────────────────

    // localhost:8080/member/list?keyword
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword, Model model)
    {
        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("members", memberService.search(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("members", memberService.findAll());
        }
        return "member/list";
    }
    // ─── 등록 폼 ──────────────────────────────────────────────
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("member", new JpaMember());
        return "member/form";
    }
    // ─── 등록 처리 ─────────────────────────────────────────────
    @PostMapping("/new")
    public String create(@Valid @ModelAttribute JpaMember jpaMember,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "member/form";
        try {
            memberService.save(jpaMember);
            redirectAttributes.addFlashAttribute("message", "회원이 등록되었습니다.");
        } catch (IllegalStateException e) {
            result.rejectValue("email", "duplicate", e.getMessage());
            return "member/form";
        }
        return "redirect:/member/list";
    }
    // ─── 수정 폼 ──────────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberService.findById(id));
        return "member/editForm";
    }
    // ─── 수정 처리 ─────────────────────────────────────────────
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute JpaMember jpaMember,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "member/editForm";
        memberService.update(id, jpaMember);
        redirectAttributes.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
        return "redirect:/member/list";
    }
    // ─── 삭제 처리 ─────────────────────────────────────────────
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes
            redirectAttributes) {
        memberService.delete(id);
        redirectAttributes.addFlashAttribute("message", "회원이 삭제되었습니다.");
        return "redirect:/member/list";
    }
}

