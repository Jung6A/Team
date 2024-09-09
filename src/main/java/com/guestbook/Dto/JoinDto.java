package com.guestbook.Dto;

import com.guestbook.Entity.Member;
import com.guestbook.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
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

    private MultipartFile profileImagePath;  // 프로필 이미지 파일

    // 프로필 이미지 이름은 저장하지 않고, URL만 저장
    // private String profileImageName;  // 불필요한 필드 (주석 처리)

    private String nickName;

    private String intro;

    // DTO -> Entity 회원가입 시 동작 메서드
    public Member createEntity(PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setNickName(this.nickName);
        member.setEmail(this.email);
        member.setUserId(this.userId);
        member.setIntro(this.intro);
        member.setRole(Role.USER);
        String pw = passwordEncoder.encode(this.password);
        member.setPassword(pw);
        // profileImageName 필드는 저장하지 않음
        return member;
    }

    // Entity -> DTO
    public static JoinDto of(Member member){
        JoinDto joinDto = new JoinDto();
        joinDto.setNickName(member.getNickName());
        joinDto.setEmail(member.getEmail());
        joinDto.setUserId(member.getUserId());
        joinDto.setIntro(member.getIntro());
        // profileImageName 필드는 사용하지 않음
        return joinDto;
    }
}
