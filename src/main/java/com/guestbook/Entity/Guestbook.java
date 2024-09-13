package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String content;
    private String writer;

    @Column(name = "guestbook_id", unique = true, nullable = false)
    private String guestbookId;

    @OneToMany(mappedBy = "guestbook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GuestbookContent> contents = new ArrayList<>();

    public static Guestbook createGuestbook(Member member, String content, String writer, String guestbookId) {
        Guestbook guestbook = new Guestbook();
        guestbook.setMember(member);
        guestbook.setContent(content);
        guestbook.setWriter(writer);
        guestbook.setGuestbookId(guestbookId);
        return guestbook;
    }
}
