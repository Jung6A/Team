package com.guestbook.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String userId;  // 사용자 아이디
    private String password;  // 비밀번호
}
