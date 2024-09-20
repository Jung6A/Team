package com.guestbook.Control;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Dto.JoinDto;
import com.guestbook.Service.GuestService;
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

    // MemberService와 GuestService를 주입받음
    private final MemberService memberService;
    private final GuestService guestService;

    // 메인 페이지 요청을 처리하는 메서드
    @GetMapping("/")
    public String main(Model model) {
        // 모든 회원 정보를 가져옴
        List<JoinDto> memberList = memberService.getAllMembers();

        // 각 회원에 대해 방명록 내용을 가져와 JoinDto에 추가
        for (JoinDto member : memberList) {
            List<GuestbookContentDto> guestbookContents = guestService.getGuestbookContentsByMemberId(member.getUserId());
            // JoinDto에 방명록 내용을 설정
            member.setGuestbookContents(guestbookContents); // JoinDto에 방명록 내용 추가
        }

        // 모든 회원 리스트를 모델에 추가
        model.addAttribute("memberLists", memberList);

        // 메인 페이지 템플릿 이름 반환
        return "index"; // 메인 페이지 템플릿 이름
    }

    // 특정 회원의 상세 정보를 조회하는 메서드
    @GetMapping("/members/{userId}")
    public String getMember(@PathVariable("userId") String userId, Model model) {
        // userId를 이용해 회원 정보를 조회
        Member member = memberService.getMember(userId);
        // 조회한 회원 정보를 JoinDto로 변환하여 모델에 추가
        JoinDto joinDto = JoinDto.of(member);
        model.addAttribute("member", joinDto);

        // 해당 회원의 방명록 내용을 가져와 모델에 추가
        List<GuestbookContentDto> guestbookContents = guestService.getGuestbookContentsByMemberId(userId);
        model.addAttribute("guestbookContents", guestbookContents);

        // 회원 상세 페이지 템플릿 이름 반환
        return "guestbook/detail"; // 회원 상세 페이지 템플릿 이름
    }
}
