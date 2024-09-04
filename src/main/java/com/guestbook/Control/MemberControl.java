package com.guestbook.Control;

import com.guestbook.Dto.signInDto;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberControl {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 로그인 페이지 요청
    @GetMapping("/login")
    public String loginPage(Model model){

        return "member/login";
    }

    // 회원가입 페이지 요청
    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("signInDto", new signInDto());
        return "member/join";
    }

    //회원가입 요청(저장)
    @PostMapping("/join")
    public String join(@Valid signInDto signInDto,
                       BindingResult bindingResult, Model model){
        if( bindingResult.hasErrors() ){ // 유효하지 않은 값 존재
            return "member/join";
        }
        try {
            memberService.saveMember(signInDto, passwordEncoder);
        }catch(IllegalStateException e1){
            bindingResult.rejectValue("userId","error.signInDto", e1.getMessage());
            return "member/join";
        }catch(IllegalArgumentException e2){
            bindingResult.rejectValue("email","error.signInDto", e2.getMessage());
            return "member/join";
        }

        return "redirect:/member/join";
    }

//    // 로그인 실패 - 아이디나 비밀번호 틀린경우
//    @GetMapping("/signIn/error")
//    public String loginFail(Model model){
//        model.addAttribute("loginFailMsg",
//                "아이디 또는 비밀번호가 올바르지 않습니다.");
//        return "member/login";
//    }
}
