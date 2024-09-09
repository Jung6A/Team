package com.guestbook.Service;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Member;
import com.guestbook.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Value("${itemImgPath}")
    private String imgPath;

    private final FileService fileService;

    // 이미지 파일 저장 및 경로 설정
    public void saveItemImg(Member member, MultipartFile multipartFile) throws Exception {
        String originalName = multipartFile.getOriginalFilename(); // 이미지 원본 이름
        String profileImagePath = "";
        String profileImageName = "";

        if (!StringUtils.isEmpty(originalName)) { // 원본 이미지 이름이 존재할 때
            profileImageName = fileService.uploadFile(imgPath, originalName, multipartFile.getBytes());
            if (profileImageName != null) {
                profileImagePath = "/images/" + profileImageName; // 웹에서 사용할 이미지 경로
                member.setProfileImagePath(profileImagePath);
                member.setProfileImageName(profileImageName);
            } else {
                throw new IllegalStateException("파일 업로드 실패");
            }
        }

        memberRepository.save(member); // Member 엔티티 저장
    }

    // 회원 가입 폼의 내용을 데이터베이스에 저장
    public void saveMember(JoinDto joinDto, PasswordEncoder passwordEncoder) {
        Member member = joinDto.createEntity(passwordEncoder);

        // 아이디와 이메일 중복 여부 검사
        validUserIdEmail(member);

        // 이미지 파일 처리
        MultipartFile profileImage = joinDto.getProfileImagePath();
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                saveItemImg(member, profileImage);
            } catch (Exception e) {
                throw new IllegalStateException("이미지 파일 처리 오류", e);
            }
        } else {
            memberRepository.save(member); // 이미지가 없을 경우에도 회원 정보 저장
        }
    }

    private void validUserIdEmail(Member member) {
        if (memberRepository.findByUserId(member.getUserId()) != null) {
            throw new IllegalStateException("이미 가입된 아이디입니다.");
        }
        if (memberRepository.findByEmail(member.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    public Member getMember(String userId) {
        Member member=memberRepository.findByUserId(userId);

        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 시 입력한 아이디로 계정 조회
        Member member = memberRepository.findByUserId(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }
        // 입력한 비밀번호와 조회한 계정 비밀번호 비교를 위해 반환
        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(member.getRole().toString()).build();
    }
}
