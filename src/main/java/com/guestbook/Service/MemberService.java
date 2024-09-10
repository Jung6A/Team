package com.guestbook.Service;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.Member;
import com.guestbook.Repository.GuestRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final GuestRepository guestRepository;
    private final FileService fileService;

    @Value("${uploadPath}")
    private String uploadPath;

    public void saveMember(JoinDto joinDto, PasswordEncoder passwordEncoder) {
        Member member = joinDto.createEntity(passwordEncoder);

        // 아이디와 이메일 중복 여부 검사
        validateUserIdEmail(member);

        // 프로필 이미지 처리
        MultipartFile profileImage = joinDto.getProfileImageUrl();
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                // 파일을 지정된 경로에 저장하고 파일 이름을 반환받음
                String profileImageName = fileService.uploadFile(uploadPath, profileImage.getOriginalFilename(), profileImage.getBytes());
                // 파일 이름을 저장하고 URL을 설정
                member.setProfileImageName(profileImageName);
                member.setProfileImageUrl("/guestbook/" + profileImageName);
            } catch (Exception e) {
                // 파일 처리 오류 발생 시 예외 처리
                throw new IllegalStateException("이미지 파일 처리 오류", e);
            }
        }

        // 회원 정보 저장
        memberRepository.save(member);

        //회원가입 시 회원 전용 방명록 테이블 생성
        Guestbook guestbook=Guestbook.createGuestbook(member);
        guestRepository.save(guestbook);
    }

    private void validateUserIdEmail(Member member) {
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
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
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
            throw new UsernameNotFoundException("해당 회원을 찾을 수 없습니다: " + userId);
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

    // 회원 정보를 userId로 찾는 메서드 추가
    public Member findById(String userId) {
        // 회원을 userId로 검색
        Member member = memberRepository.findByUserId(userId);
        if (member == null) {
            throw new IllegalStateException("해당 회원을 찾을 수 없습니다: " + userId);
        }
        return member;
    }
}
