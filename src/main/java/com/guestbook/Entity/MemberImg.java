package com.guestbook.Entity;

import com.guestbook.constant.Role;
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

    private long memberId; //어느 유저의 프로필 사진인지
    
    //나중에 이미지 주소 관련 추가
}
