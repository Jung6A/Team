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
    private String name;
    private String password;

    private String profileImagePath;

    @Column(unique = true)
    private String email;

    private Role role;

}
