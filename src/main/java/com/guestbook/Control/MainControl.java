package com.guestbook.Control;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Service.MemberService;
import com.guestbook.Entity.Member;
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
        List<JoinDto> memberList = memberService.getAllMembers();
        model.addAttribute("memberLists", memberList);
        return "index";
    }

    @GetMapping("/members/{userId}")
    public String getMember(@PathVariable("userId") String userId, Model model) {
        Member member = memberService.getMember(userId);
        JoinDto joinDto = JoinDto.of(member);
        model.addAttribute("member", joinDto);
        return "guestbook/detail"; // 회원 상세 페이지 템플릿 이름
    }
}
