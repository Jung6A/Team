package com.guestbook.Control;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.Member;
import com.guestbook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class GuestControl {
    private final MemberService memberService;

    @GetMapping("/guest/{userId}")
    public String guestHome(Model model, @PathVariable("userId") String userId) {
        // 특정 회원의 정보를 가져와서 모델에 추가
        Member member = memberService.getMember(userId);
        JoinDto joinDto = JoinDto.of(member);
        model.addAttribute("member", joinDto);
        model.addAttribute("guestbook", new Guestbook()); // 방명록 객체 추가
        return "guestbook/detail";
    }
}
