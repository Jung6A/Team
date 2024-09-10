package com.guestbook.Control;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Member;
import com.guestbook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainControl {

    private final MemberService memberService;

    @GetMapping("/")
    public String main(Model model) {
        // 모든 회원 정보를 메인화면에 전달
        List<JoinDto> memberList = memberService.getAllMembers();
        model.addAttribute("memberLists", memberList);
        return "index";
    }

    @GetMapping("/member/{userId}")
    public String getMember(@PathVariable("userId") String userId, Model model) {
        // 특정 회원 정보를 가져와서 모델에 추가
        Member member = memberService.getMember(userId);
        JoinDto joinDto = JoinDto.of(member);
        model.addAttribute("member", joinDto);
        return "memberDetail"; // 회원 상세 페이지 템플릿 이름
    }
}
