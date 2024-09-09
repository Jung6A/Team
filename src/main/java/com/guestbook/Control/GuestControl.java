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

        model.addAttribute("member", memberService.getMember(userId));
        model.addAttribute("guestbook", new Guestbook());
        return "guestbook/detail";
    }
}
