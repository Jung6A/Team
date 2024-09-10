package com.guestbook.Control;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import com.guestbook.Entity.Member;
import com.guestbook.Service.GuestService;
import com.guestbook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class GuestControl {

    private final MemberService memberService;
    private final GuestService guestService;

    @GetMapping("/guest/{userId}")
    public String guestHome(Model model, @PathVariable("userId") String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return "redirect:/member/login";
        }

        Member member = memberService.getMember(userId);
        JoinDto joinDto = JoinDto.of(member);
        model.addAttribute("member", joinDto);
        Guestbook guestbook=guestService.getGuestbook(member.getId()); //해당 회원 방명록 불러오기
        model.addAttribute("guestbook", guestbook);
        model.addAttribute("guestContent", new GuestbookContent()); //방명록 작성 시 사용하는 방명록 컨텐츠 객체 생성
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
