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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MemberControl {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 로그인 페이지 요청
    @GetMapping("/member/login")
    public String loginPage(Model model){
        return "member/login";
    }

    // 회원가입 페이지 요청
    @GetMapping("/member/join")
    public String joinPage(Model model){
        model.addAttribute("joinDto", new JoinDto());
        return "member/join";
    }

    @PostMapping("/member/join")
    public String join(@Valid JoinDto joinDto, BindingResult bindingResult, Model model){
        MultipartFile profileImage = joinDto.getProfileImage();
        String profileImagePath = null;

        if (profileImage != null && !profileImage.isEmpty()) {
            // 파일 이름과 저장 경로 설정
            String fileName = profileImage.getOriginalFilename();
            String savePath = "path/to/save/" + fileName;

            // 파일을 저장
            try {
                profileImage.transferTo(new File(savePath));
                profileImagePath = savePath;  // 저장한 경로를 엔티티에 저장할 수 있도록 준비
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Member 엔티티 생성 및 파일 경로 설정
        Member member = new Member();
        member.setUserId(joinDto.getUserId());
        member.setPassword(joinDto.getPassword());
        member.setEmail(joinDto.getEmail());
        member.setProfileImagePath(profileImagePath);  // 파일 경로를 엔티티에 저장

//        // 회원 저장 로직 (예: repository.save(member))
//        MemberRepository.save(member);

        if( bindingResult.hasErrors() ){ // 유효하지 않은 값 존재
            return "member/join";
        }
        try {
            memberService.saveMember(joinDto, passwordEncoder);
        }catch(IllegalStateException e1){
            bindingResult.rejectValue("userId","error.signInDto", e1.getMessage());
            return "member/join";
        }catch(IllegalArgumentException e2){
            bindingResult.rejectValue("email","error.signInDto", e2.getMessage());
            return "member/join";
        }

        return "redirect:/join";
    }

    // 로그인 실패 - 아이디나 비밀번호 틀린경우
    @GetMapping("/member/login/error")
    public String loginFail(Model model){
        model.addAttribute("loginFailMsg",
                "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "member/login";
    }
}
