package com.guestbook.Service;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.GuestbookContent;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Value("${itemImgPath}")
    private String imgPath;

    // 회원 가입 폼의 내용을 데이터베이스에 저장
    public void saveMember(JoinDto joinDto, PasswordEncoder passwordEncoder) {
        Member member = joinDto.createEntity(passwordEncoder);

        // 아이디와 이메일 중복 여부 검사
        validUserIdEmail(member);

        // 프로필 이미지 처리
        MultipartFile profileImage = joinDto.getProfileImageUrl();
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String profileImageName = fileService.uploadFile(imgPath, profileImage.getOriginalFilename(), profileImage.getBytes());
                member.setProfileImageName(profileImageName);
                member.setProfileImageUrl("/images/" + profileImageName);
            } catch (Exception e) {
                throw new IllegalStateException("이미지 파일 처리 오류", e);
            }
        }

        // 회원 정보 저장
        memberRepository.save(member);
    }

    private void validUserIdEmail(Member member) {
        // 아이디 중복 체크
        if (memberRepository.findByUserId(member.getUserId()) != null) {
            throw new IllegalStateException("이미 가입된 아이디입니다.");
        }
        // 이메일 중복 체크
        if (memberRepository.findByEmail(member.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }
        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(member.getRole().toString()).build();
    }

    public Member getMember(String userId) {
        // 회원을 userId로 검색
        Member member = memberRepository.findByUserId(userId);
        if (member == null) {
            throw new UsernameNotFoundException("해당 회원을 찾을 수 없습니다.");
        }
        return member;
    }

    // 모든 회원 정보를 가져오는 메서드 추가
    public List<JoinDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(JoinDto::of)
                .collect(Collectors.toList());
    }
}
