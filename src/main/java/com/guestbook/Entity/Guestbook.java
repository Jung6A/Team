package com.guestbook.Entity;

import com.guestbook.constant.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="guestbook_detail")
public class Guestbook {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="guestbook_id")
    private long id;

    private String userId;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
