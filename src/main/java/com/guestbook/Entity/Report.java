package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Member reporter; // 신고자- 현재로그인한 사람

    @ManyToOne
    @JoinColumn(name = "reported_guest_id")
    private GuestbookContent reportedGuest; // 신고 당한 사람 (방명록 작성자)

    private String reason; // 신고 사유

    private String reasonDetail; // 기타 사유

    private LocalDateTime reportDate = LocalDateTime.now(); // 신고 날짜
}
