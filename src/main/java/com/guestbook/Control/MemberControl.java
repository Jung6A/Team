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

    // MemberService와 PasswordEncoder를 주입받음
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 로그인 페이지 요청을 처리하는 메서드
    @GetMapping("/login")
    public String loginPage(Model model) {
        // 로그인 페이지 템플릿을 반환
        return "member/login";
    }

    // 회원가입 페이지 요청을 처리하는 메서드
    @GetMapping("/join")
    public String joinPage(Model model) {
        // 회원가입 폼에 사용할 JoinDto를 모델에 추가
        model.addAttribute("joinDto", new JoinDto());
        // 회원가입 페이지 템플릿을 반환
        return "member/join";
    }

    // 회원가입 처리 메서드
    @PostMapping("/join")
    public String join(@Valid JoinDto joinDto, BindingResult bindingResult, Model model) {
        // 입력된 데이터에 오류가 있으면 회원가입 페이지로 돌아감
        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        try {
            // 회원 정보를 저장 (회원가입 로직 실행)
            memberService.saveMember(joinDto, passwordEncoder);
        } catch (IllegalStateException e1) {
            // 중복된 userId가 있는 경우 오류 메시지를 추가하고 다시 회원가입 페이지로 이동
            bindingResult.rejectValue("userId", "error.joinDto", e1.getMessage());
            return "member/join";
        } catch (IllegalArgumentException e2) {
            // 중복된 email이 있는 경우 오류 메시지를 추가하고 다시 회원가입 페이지로 이동
            bindingResult.rejectValue("email", "error.joinDto", e2.getMessage());
            return "member/join";
        } catch (Exception e) {
            // 그 외 예외가 발생하면 RuntimeException을 발생시킴
            throw new RuntimeException(e);
        }

        // 회원가입 성공 시 로그인 페이지로 리디렉션
        return "redirect:/member/login";
    }

    // 로그인 실패 시 처리하는 메서드
    @GetMapping("/login/error")
    public String loginFail(Model model) {
        // 로그인 실패 메시지를 모델에 추가
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        // 로그인 페이지로 다시 이동
        return "member/login";
    }
}
