package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="guestbook_profile_img")
public class ProfileImg {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="profile_img_id")
    private long id;

    private String imgName;
    private String imgUrl;
    private String originalName;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member; //어느 유저의 프로필 사진인지
}
