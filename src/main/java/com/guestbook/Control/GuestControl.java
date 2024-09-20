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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guestbook")
public class GuestControl {

    // GuestService와 MemberService를 주입받음
    private final GuestService guestService;
    private final MemberService memberService;

    // 방명록 작성 완료 후 처리하는 메서드
    @PostMapping("/writeComplete")
    public String writeGuestbook(@RequestParam("content") String content,
                                 @RequestParam("guestbookId") String guestbookId) {
        // 현재 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 정보가 없거나 인증되지 않은 경우 로그인 페이지로 리디렉션
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login";
        }

        // 인증된 사용자의 이름(아이디)을 가져옴
        String username = authentication.getName();

        // 방명록 내용을 담는 DTO를 생성하고 필드값을 설정
        GuestbookContentDto guestbookContentDto = new GuestbookContentDto();
        guestbookContentDto.setContent(content);
        guestbookContentDto.setGuestbookId(guestbookId);
        guestbookContentDto.setWriter(username);
        guestbookContentDto.setCreatedDate(LocalDateTime.now());

        // 방명록 내용을 저장
        guestService.saveGuestbookContent(guestbookContentDto);

        // 방명록 상세 페이지로 리디렉션
        return "redirect:/guestbook/detail?guestbookId=" + guestbookId;
    }

    // 방명록 상세 페이지를 조회하는 메서드
    @GetMapping("/detail")
    public String getGuestbookDetail(@RequestParam("guestbookId") String guestbookId, Model model) {
        // guestbookId로 회원 정보를 조회
        Member member = memberService.getMember(guestbookId);
        // 조회한 회원 정보를 JoinDto로 변환하여 모델에 추가
        JoinDto joinDto = JoinDto.of(member);
        model.addAttribute("member", joinDto);

        // 방명록에 작성된 모든 내용들을 리스트로 가져와 모델에 추가
        List<GuestbookContentDto> guestbookContents = guestService.getGuestbookContentsByMemberId(guestbookId);
        model.addAttribute("guestbookContents", guestbookContents);

        // guestbook/detail 뷰를 반환 (해당 뷰 페이지로 이동)
        return "guestbook/detail";
    }
}
