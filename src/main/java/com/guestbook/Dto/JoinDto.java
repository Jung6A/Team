package com.guestbook.Dto;

import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import com.guestbook.Entity.Member;
import com.guestbook.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class JoinDto {
    private Long id;  // 회원 ID

    @NotBlank(message = "아이디는 필수 입니다.")
    private String userId;  // 사용자 아이디

    @NotBlank(message = "이메일을 작성해주세요")
    private String email;  // 이메일

    @Size(min = 4, max = 12, message = "비밀번호는 4~12자리 입니다.")
    private String password;  // 비밀번호

    @NotNull(message = "프로필 파일은 필수 등록사항 입니다.")
    private MultipartFile profileImageUrl;  // 프로필 이미지 파일 경로

    private String profileImageName;  // 프로필 이미지 이름

    private String nickName;  // 닉네임

    // 이미지 저장 경로를 위한 DTO
    private MemberImgDto memberImgDto;
    private Long memberImgId;  // 프로필 이미지 ID

    private String intro;  // 소개

    private List<GuestbookContentDto> guestbookContents;  // 방명록 내용을 추가

    // Member 엔티티로 변환하는 메서드
    public Member createEntity(PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setNickName(this.nickName);  // 닉네임 설정
        member.setEmail(this.email);  // 이메일 설정
        member.setUserId(this.userId);  // 사용자 아이디 설정
        member.setIntro(this.intro);  // 소개 설정
        member.setRole(Role.USER);  // 역할 설정 (기본값 USER)

        String pw = passwordEncoder.encode(this.password);  // 비밀번호 암호화
        member.setPassword(pw);  // 암호화된 비밀번호 설정

        member.setProfileImageName(this.profileImageName);  // 프로필 이미지 이름 설정

        return member;
    }

    // Member 엔티티를 DTO로 변환하는 메서드
    public static JoinDto of(Member member) {
        JoinDto joinDto = new JoinDto();
        joinDto.setNickName(member.getNickName());  // 닉네임 설정
        joinDto.setEmail(member.getEmail());  // 이메일 설정
        joinDto.setUserId(member.getUserId());  // 사용자 아이디 설정
        joinDto.setIntro(member.getIntro());  // 소개 설정
        joinDto.setProfileImageName(member.getProfileImageName());  // 프로필 이미지 이름 설정

        // 방명록 내용 추가
        joinDto.setGuestbookContents(member.getGuestbooks().stream()
                .flatMap(guestbook -> guestbook.getContents().stream()  // Guestbook의 Content 가져오기
                        .map(GuestbookContentDto::of))  // GuestbookContent를 DTO로 변환
                .collect(Collectors.toList()));  // 리스트로 수집

        return joinDto;
    }
}
