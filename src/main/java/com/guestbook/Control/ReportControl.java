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

    // ReportService와 MemberRepository를 주입받음
    @Autowired
    private ReportService reportService;

    @Autowired
    private MemberRepository memberRepository;

    // 신고 처리 요청을 처리하는 메서드
    @PostMapping("/report/submit")
    public String submitReport(
            @RequestParam("writerId") Long writerId,        // 신고 당한 사람의 ID
            @RequestParam("reason") String reason,          // 신고 사유
            @RequestParam(value = "reasonDetail", required = false) String reasonDetail, // 상세 신고 사유 (선택 사항)
            Principal principal) {                          // 현재 로그인한 사용자 정보를 가져오기 위한 객체

        // 현재 로그인한 사용자의 정보를 가져옴
        Optional<Member> optionalMember = memberRepository.findByUserId(principal.getName());

        // 사용자가 존재하는 경우
        if (optionalMember.isPresent()) {
            Member reporter = optionalMember.get();

            // 신고 내용을 처리 (reportService로 신고 로직 실행)
            // writerUserId는 신고당한 사람의 userId
            String writerUserId = reportService.submitReport(reporter.getId(), writerId, reason, reasonDetail);

            // 신고가 완료되면 해당 방명록 페이지로 리다이렉트
            return "redirect:/guestbook/detail?guestbookId=" + writerUserId;
        } else {
            // 사용자가 존재하지 않으면 에러 페이지로 리다이렉트
            return "redirect:/error";
        }
    }
}
