package com.guestbook.Dto;

import com.guestbook.Entity.Member;
import com.guestbook.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class JoinDto {
    private Long id;

    @NotBlank(message = "아이디는 필수 입니다.")
    private String userId;

    @NotBlank(message = "이메일을 작성해주세요")
    private String email;

    @Size(min=4 , max=12, message="비밀번호는 4~12자리 입니다.")
    private String password;

    @NotNull(message = "프로필 파일은 필수 등록사항 입니다.")
    private MultipartFile profileImageUrl;  // 프로필 이미지 파일경로

    private String profileImageName;  // 프로필 이미지 이름

    private String nickName;

    private String intro;

    public Member createEntity(PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setNickName(this.nickName);
        member.setEmail(this.email);
        member.setUserId(this.userId);
        member.setIntro(this.intro);
        member.setRole(Role.USER);
        String pw = passwordEncoder.encode(this.password);
        member.setPassword(pw);
        member.setProfileImageName(this.profileImageName); // 프로필 이미지 이름 설정
        return member;
    }

    public static JoinDto of(Member member){
        JoinDto joinDto = new JoinDto();
        joinDto.setNickName(member.getNickName());
        joinDto.setEmail(member.getEmail());
        joinDto.setUserId(member.getUserId());
        joinDto.setIntro(member.getIntro());
        joinDto.setProfileImageName(member.getProfileImageName()); // 프로필 이미지 이름 설정
        return joinDto;
    }
}
