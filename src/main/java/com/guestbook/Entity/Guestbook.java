package com.guestbook.Entity;

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
}
