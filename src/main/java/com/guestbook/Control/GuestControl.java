package com.guestbook.Control;

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
        Member member = memberService.getMember(userId); // 이제 getMember 메서드를 정상적으로 호출
        model.addAttribute("member", member);
        model.addAttribute("guestbook", new Guestbook());
        return "guestbook/detail";
    }
}
