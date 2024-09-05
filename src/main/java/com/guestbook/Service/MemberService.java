package com.guestbook.Service;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Member;
import com.guestbook.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //회원 가입폼의 내용을 데이터 베이스에 저장
    public void saveMember(JoinDto JoinDto, PasswordEncoder passwordEncoder){
        Member member = JoinDto.createEntity(passwordEncoder);
        // 아이디와 이메일 중복여부
        validUserIdEmail( member );
        memberRepository.save(member);
    }
    private void validUserIdEmail(Member member){
        Member find = memberRepository.findByUserId(member.getUserId());
        if(find!=null){
            throw new IllegalStateException("이미 가입된 아이디입니다.");
        }
        find = memberRepository.findByEmail(member.getEmail());
        if(find!=null){
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인시 입력한 아이디로 계정 조회
        Member member=memberRepository.findByUserId(username);
        if(member == null){
            throw new UsernameNotFoundException(username);
        }
        // 입력한 비밀번호와 조회한 계정 비밀번호 비교를 위해 반환
        // User 는 org.springframework.security.core.userdetails.User 걸로 import
        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(member.getRole().toString()).build();
    }
}
