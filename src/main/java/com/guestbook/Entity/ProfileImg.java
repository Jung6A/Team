package com.guestbook.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="guestbook_profile_img")
public class ProfileImg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_img_id")
    private long id;

    private String imgName;
    private String imgUrl;
    private String originalName;
    private String nickName;

    private String intro;
    private String repImgYn; // 대표이미지 설정

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member; // 어느 유저의 프로필 사진인지
}
