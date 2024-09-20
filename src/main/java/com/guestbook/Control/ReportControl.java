package com.guestbook.Control;

import com.guestbook.Entity.Member;
import com.guestbook.Repository.MemberRepository;
import com.guestbook.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ReportControl {

    @Autowired
    private ReportService reportService;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/report/submit")
    public String submitReport(
            @RequestParam("writerId") Long writerId,
            @RequestParam("reason") String reason,
            @RequestParam(value = "reasonDetail", required = false) String reasonDetail,
            Principal principal) {

        // 현재 로그인한 사용자 정보 가져오기
        Optional<Member> optionalMember = memberRepository.findByUserId(principal.getName());

        if (optionalMember.isPresent()) {
            Member reporter = optionalMember.get();

            // 신고 처리 , writerUserId-신고당한 사람 ID
            String writerUserId= reportService.submitReport(reporter.getId(),writerId,reason, reasonDetail);

            return "redirect:/"; // 신고 완료 후 리다이렉트
        } else {
            // 사용자가 존재하지 않는 경우 처리 (예: 에러 페이지로 리다이렉트)
            return "redirect:/error";
        }
    }
}
