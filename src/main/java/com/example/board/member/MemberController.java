package com.example.board.member;

import com.example.board.member.dto.LoginForm;
import com.example.board.member.dto.SignUpForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 화면
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "members/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignUpForm signUpForm, BindingResult result) {
        if (result.hasErrors()) return "members/signup";
        try {
            memberService.signup(signUpForm);
        } catch (IllegalArgumentException e) {
            result.reject("signupFail", e.getMessage());
            return "members/signup";
        }
        return "redirect:/login";
    }

    // 로그인 화면
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, HttpSession session) {
        if (result.hasErrors()) return "members/login";
        try {
            Member member = memberService.login(loginForm);
            // 로그인 상태를 세션에 저장 (서버가 "이 사람 로그인했음"을 기억)
            session.setAttribute("loginMemberId", member.getId());
            session.setAttribute("loginMemberNickname", member.getNickname());
        } catch (IllegalArgumentException e) {
            result.reject("loginFail", e.getMessage());
            return "members/login";
        }
        return "redirect:/posts";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 비우기 = 로그아웃
        return "redirect:/posts";
    }
}
