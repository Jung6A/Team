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

    private String userId; //받는 사람. 누구의 방명록에 쓰였는지
    
    private String writer; //작성자
    private String content; //내용
    
    //방명록 받는 사람, 작성자, 내용
}

