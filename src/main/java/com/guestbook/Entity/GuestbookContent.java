package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class GuestbookContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY 전략 사용
    @Column(name = "guestbook_content_id")
    private Long id;

    private String writer; // 작성자
    private String content; // 방명록 내용

    @ManyToOne
    @JoinColumn(name = "guestbook_id")
    private Guestbook guestbook; // 누구의 방명록에 썼는지

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate; // 작성 시간

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now(); // 엔티티 저장 시 작성 시간 설정
    }
}
