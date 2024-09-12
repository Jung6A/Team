package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MemberImg {
    @Id
    @Column(name = "member_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String imgName; //이미지 이름
    private String imgUrl; //이미지 Url
    private String OriginalName; //이미지 원본 이름
    //대표이미지 설정은 필요 없으니 제외

    @OneToOne
    @JoinColumn(name = "id")
    private Member member; //어떤 멤버의 프로필 사진인지
}
