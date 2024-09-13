package com.guestbook.Service;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.Member;
import com.guestbook.Entity.MemberImg;
import com.guestbook.Repository.GuestBookRepository;
import com.guestbook.Repository.MemberImgRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberImgRepository memberImgRepository;
    private final GuestBookRepository guestBookRepository;
    private final FileService fileService;
    private final MemberImgService memberImgService;

    @Value("${uploadPath}")
    private String uploadPath;

    public void saveMember(JoinDto joinDto, PasswordEncoder passwordEncoder) throws Exception {
        Member member = joinDto.createEntity(passwordEncoder);

        // 아이디와 이메일 중복 여부 검사
        validateUserIdEmail(member);

        // 프로필 이미지 처리
        MultipartFile profileImage = joinDto.getProfileImageUrl();
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String profileImageName = fileService.uploadFile(uploadPath, profileImage.getOriginalFilename(), profileImage.getBytes());
                member.setProfileImageName(profileImageName);
                member.setProfileImageUrl("/guestbook/" + profileImageName);
            } catch (Exception e) {
                throw new IllegalStateException("이미지 파일 처리 오류", e);
            }
        }

        // 회원 정보 저장
        memberRepository.save(member);

        // 방명록 생성
        Guestbook guestbook = Guestbook.createGuestbook(member, "", member.getUserId(), member.getUserId());
        guestBookRepository.save(guestbook);
    }

    private void validateUserIdEmail(Member member) {
        // 아이디 중복 체크
        if (memberRepository.findByUserId(member.getUserId()).isPresent()) {
            throw new IllegalStateException("이미 가입된 아이디입니다.");
        }
        // 이메일 중복 체크
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public Member getMember(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다: " + userId));
    }

    public List<JoinDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(JoinDto::of)
                .collect(Collectors.toList());
    }

    public Member findById(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("해당 회원을 찾을 수 없습니다: " + userId));
    }

    public List<GuestbookContentDto> getGuestbookContentsByMemberId(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("해당 회원을 찾을 수 없습니다: " + userId));

        return member.getGuestbooks().stream()
                .flatMap(guestbook -> guestbook.getContents().stream())
                .map(content -> {
                    GuestbookContentDto dto = GuestbookContentDto.of(content);
                    // 방명록 작성자의 프로필 이미지 이름을 설정
                    dto.setProfileImageName(content.getGuestbook().getMember().getProfileImageName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
