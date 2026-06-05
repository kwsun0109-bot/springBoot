package com.example.member.contoller;

import com.example.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @GetMapping("/list")
    public void list(@RequestParam("keyword") String keyword,
                     @RequestParam("age") int age,
                     @RequestParam("addr") String addr,
                     Model model) {   // <-- view
        //System.out.println(("목록 조회 화면"));
        log.info("목록 조회 화면");
        log.info("keyword : {}", keyword);
        log.info("age : {}", age);
        log.info("addr : {}", addr);

        model.addAttribute("name", keyword);
        model.addAttribute("age", age);
        model.addAttribute("addr", addr);
    }

    @GetMapping("/list2")
    public void list2(@RequestParam("keyword") String keyword,
                     @RequestParam("age") int age,
                     @RequestParam("phone") String phone,
                     @RequestParam("addr") String addr,
                     Model model) {   // <-- view
        //System.out.println(("목록 조회 화면"));
        log.info("목록 조회 화면2");
        log.info("keyword : {}", keyword);
        log.info("age : {}", age);
        log.info("phone : {}", phone);
        log.info("addr : {}", addr);

        model.addAttribute("name", keyword);
        model.addAttribute("age", age);
        model.addAttribute("phone", phone);
        model.addAttribute("addr", addr);
    }

    @PostMapping("/new")
    public void newMember(@RequestBody Member member) { // json 객체를 Java 객체로..
        //System.out.println("등록화면");
        log.info("등록화면");
        log.info("Member : {}", member);
        log.info("Member : {}", member.getId());
        log.info("Member : {}", member.getName());
        log.info("Member : {}", member.getEmail());
    }

    @PostMapping("/edit/{id}")
    public void editMember(@PathVariable("id") String id) {
        //System.out.println("수정처리");
        log.info("수정처리");
        log.info("ID : {}", id);
    }

    @PostMapping("/delete")
    public void deleteMember() {
        //System.out.println("삭제처리");
        log.info("삭제처리");
    }
}
