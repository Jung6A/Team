package com.guestbook.Control;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import com.guestbook.Entity.Member;
import com.guestbook.Service.GuestService;
import com.guestbook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestControl {
    private final MemberService memberService;
    private final GuestService guestService;

    @GetMapping("/{userId}")
    public String guestHome(Model model, @PathVariable("userId") String userId) {
        Member member = memberService.getMember(userId); // 이제 getMember 메서드를 정상적으로 호출
        model.addAttribute("member", member);
        model.addAttribute("guestbook", new Guestbook());
        model.addAttribute("guestContent", new GuestbookContent());
        return "guestbook/detail";
    }

    @PostMapping("/writeComplete")
    public String save(@Valid GuestbookContentDto guestbookContentDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "/guestbook/detail";
        }
        guestService.save(guestbookContentDto);

        return "/guestbook/detail";
    }
}
