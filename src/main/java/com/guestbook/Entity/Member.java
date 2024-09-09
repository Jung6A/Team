package com.guestbook.Entity;

import com.guestbook.constant.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="project_member") // 테이블 이름의 공백 제거
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userId;
    private String nickName;
    private String password;

    private String intro;

    private String profileImagePath; // 이미지 경로
    private String profileImageName; // 이미지 파일명

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
