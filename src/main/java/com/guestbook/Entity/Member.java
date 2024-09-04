package com.guestbook.Entity;


import com.guestbook.constant.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name="project_member ")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userId;
    private String password;
    private String name;

    @Column(unique = true)
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime regTime; //회원가입 날짜와 시간 저장
    private LocalDateTime updateTime; // 회원 정보 수정 날짜오 시간 저장

}
