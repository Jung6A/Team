package com.guestbook.Control;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainControl {

    private final MemberService memberService;

    @GetMapping("/")
    public String main(Model model) {

        // 회원 정보를 메인화면에 전달
        List<JoinDto> memberList = memberService.getAllMembers();
        model.addAttribute("memberLists", memberList);

        return "index";
    }
}
