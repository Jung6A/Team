package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class GuestbookContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="guestbook_content_id")
    private Long id;

    private String writer; //작성자
    private String Content; //방명록 내용

    @ManyToOne
    @JoinColumn(name="guestbook_id")
    private Guestbook guestbook; //누구의 방명록에 썼는지
}
