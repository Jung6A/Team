package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="guestbook_member_img")
public class MemberImg {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="member_img_id")
    private long id;
}
