package com.example.member.contoller;

import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j    // --> 자동 생성해줌, [private static final Logger log = LoggerFactory.getLogger(MyService.class);]
public class MemberController {

    @Autowired   // 제어의 역전
    private MemberService memberService; // = new MemberService(); 생성해 준다 @Autowired 어노테이션이.....

//    @GetMapping("/list")
//    public void list(@RequestParam("keyword") String keyword,
//                     @RequestParam("age") int age,
//                     @RequestParam("addr") String addr,
//                     Model model) {   // <-- view
//        //System.out.println(("목록 조회 화면"));
//        log.info("목록 조회 화면");
//        log.info("keyword : {}", keyword);
//        log.info("age : {}", age);
//        log.info("addr : {}", addr);
//
//        model.addAttribute("name", keyword);
//        model.addAttribute("age", age);
//        model.addAttribute("addr", addr);
//    }
// ─── 목록 조회 + 검색 ──────────────────────────────────────
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        // keyword = null (파라미터 없을 때)
        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("members", memberService.search(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("members", memberService.findAll());
        }
        return "member/list"; // → templates/member/list.html
    }

//    @GetMapping("/list2")
//    public void list2(@RequestParam("keyword") String keyword,
//                     @RequestParam("age") int age,
//                     @RequestParam("phone") String phone,
//                     @RequestParam("addr") String addr,
//                     Model model) {   // <-- view
//        //System.out.println(("목록 조회 화면"));
//        log.info("목록 조회 화면2");
//        log.info("keyword : {}", keyword);
//        log.info("age : {}", age);
//        log.info("phone : {}", phone);
//        log.info("addr : {}", addr);
//
//        model.addAttribute("name", keyword);
//        model.addAttribute("age", age);
//        model.addAttribute("phone", phone);
//        model.addAttribute("addr", addr);
//    }
// ─── 등록 폼 ───────────────────────────────────────────────
    @GetMapping("/new")
    public String createFrom(Model model) {
        model.addAttribute("member", new Member());
        return "member/form";
    }

// ─── 등록 처리 ─────────────────────────────────────────────
    @PostMapping("/new")
    public String newMember(@Valid @ModelAttribute Member member,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) { // json 객체를 Java 객체로..  @RequestBody
        //System.out.println("등록화면");
        if (result.hasErrors()) {
            return "member/form";
        }
        try {
            memberService.save(member);
            redirectAttributes.addFlashAttribute("message", "회원이 등록되었습니다.");
        } catch(Exception e) {
            result.rejectValue("email", "duplicate", e.getMessage());
            return "member/form";
        }
        return "redirect:/member/list";
    }

// ─── 수정 폼 ───────────────────────────────────────────────
    //localhost:8080/member/edit/3
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberService.findById(id));
        return "member/editForm";
    }

// ─── 수정 처리 ─────────────────────────────────────────────
    @PostMapping("/edit/{id}")
    public String editMember(@PathVariable("id") Long id,
                           @Valid @ModelAttribute Member member,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "member/form";
        }
        memberService.update(id, member);
        redirectAttributes.addFlashAttribute("message", "회원 정보가 수정되었습니다.");

        return "redirect:/member/list";
    }
    //localhost:8080/member/delete/13
// ─── 삭제 처리 ─────────────────────────────────────────────
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        //System.out.println("삭제처리");
        memberService.delete(id);
        redirectAttributes.addFlashAttribute("message", "회원이 삭제되었습니다.");
        return "redirect:/member/list";
    }
}
