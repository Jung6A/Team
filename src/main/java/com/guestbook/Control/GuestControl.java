package com.guestbook.Control;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Member;
import com.guestbook.Service.GuestService;
import com.guestbook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guestbook")
public class GuestControl {

    private final GuestService guestService;
    private final MemberService memberService;

    @PostMapping("/writeComplete")
    public String writeGuestbook(@RequestParam("content") String content,
                                 @RequestParam("guestbookId") String guestbookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login";
        }

        String username = authentication.getName();

        GuestbookContentDto guestbookContentDto = new GuestbookContentDto();
        guestbookContentDto.setContent(content);
        guestbookContentDto.setGuestbookId(guestbookId);
        guestbookContentDto.setWriter(username);
        guestbookContentDto.setCreatedDate(LocalDateTime.now()); // 작성 시간 설정

        guestService.saveGuestbookContent(guestbookContentDto);

        return "redirect:/guestbook/detail?guestbookId=" + guestbookId;
    }

    @GetMapping("/detail")
    public String getGuestbookDetail(@RequestParam("guestbookId") String guestbookId, Model model) {
        Member member = memberService.getMember(guestbookId);
        JoinDto joinDto = JoinDto.of(member);
        model.addAttribute("member", joinDto);

        List<GuestbookContentDto> guestbookContents = guestService.getGuestbookContentsByMemberId(guestbookId);
        model.addAttribute("guestbookContents", guestbookContents);

        return "guestbook/detail";
    }
}
