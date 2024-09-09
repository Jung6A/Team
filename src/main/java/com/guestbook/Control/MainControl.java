package com.guestbook.Control;

import com.guestbook.Dto.ProfileImgDto;
import com.guestbook.Service.ProfileImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainControl {

    private final ProfileImgService profileImgService;

    @GetMapping("/")
    public String main(Model model) {

        //메인화면에 이미지에 사용할 이미지들가져오기
        List<ProfileImgDto> mainImgList = profileImgService.getMainImg();
        //슬라이드이미지 아래에  회원 정보 저장
        //List<JoinDto> MemberList = itemService.getMember();

        model.addAttribute("mainImg", mainImgList);
        //model.addAttribute("memberLists" , MemberList);

        return "index";
    }
}
