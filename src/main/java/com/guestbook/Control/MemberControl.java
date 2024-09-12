package com.guestbook.Control;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Member;
import com.guestbook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberControl {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "member/login";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("joinDto", new JoinDto());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid JoinDto joinDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        try {
            memberService.saveMember(joinDto, passwordEncoder);
        } catch (IllegalStateException e1) {
            bindingResult.rejectValue("userId", "error.joinDto", e1.getMessage());
            return "member/join";
        } catch (IllegalArgumentException e2) {
            bindingResult.rejectValue("email", "error.joinDto", e2.getMessage());
            return "member/join";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login/error")
    public String loginFail(Model model) {
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "member/login";
    }
}
