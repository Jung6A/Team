package com.guestbook.Service;

import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import com.guestbook.Entity.Member;
import com.guestbook.Entity.Report;
import com.guestbook.Repository.GuestRepository;
import com.guestbook.Repository.GuestbookContentRepository;
import com.guestbook.Repository.MemberRepository;
import com.guestbook.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GuestbookContentRepository guestbookContentRepository;

    //신고 처리 메서드
    public String submitReport(Long reporterId,  Long writerId, String reason, String reasonDetail) {
        Member reporter = memberRepository.findById(reporterId)
                .orElseThrow(() -> new IllegalArgumentException("신고자가 존재하지 않습니다."));
        GuestbookContent reportedGuest = guestbookContentRepository.findById( writerId )
                .orElseThrow(() -> new IllegalArgumentException("신고당한 사용자가 존재하지 않습니다."));

        Report report = new Report();
        report.setReporter(reporter); //신고자
        report.setReportedGuest(reportedGuest); //신고당한 방명록 게시자
        report.setReason(reason); //신고 사유
        report.setReasonDetail(reasonDetail); //기타 사유 (선택사항)

        reportRepository.save(report);

        return reportedGuest.getWriter();
    }
}

