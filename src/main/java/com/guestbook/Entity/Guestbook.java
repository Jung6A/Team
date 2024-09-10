package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Guestbook {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="guestbook_id")
    private long id;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member; //방명록 주인

    public static Guestbook createGuestbook(Member member) {
        Guestbook guestbook =new Guestbook();
        guestbook.setMember(member);

        return guestbook;
    }
}
//방명록과 방명록 내용 Entity를 분리 (Cart와 CartItem처럼)