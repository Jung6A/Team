package com.guestbook.Service;

import com.guestbook.Dto.JoinDto;
import com.guestbook.Entity.Member;
import com.guestbook.Entity.ProfileImg;
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

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Value("${itemImgPath}")
    private String imgPath;


    private final FileService fileService;
    private final MemberRepository itemImgRepository;

    public void saveItemImg(Member member, MultipartFile multipartFile) throws Exception {
        String originalName = multipartFile.getOriginalFilename();// 이미지원본이름
        String profileImagePath="";
        String profileImageName="";
        // 파일 업로드
//        if( !StringUtils.isEmpty( originalName )){// 업로드 한 원본 이미지이름 존재여부
//            profileImageName = fileService.uploadFile(imgPath,
//                    originalName, multipartFile.getBytes());
//            profileImagePath = "/images/"+profileImageName; // 웹에 사용할 이미지 경로
//        }
        member.setProfileImagePath(profileImagePath);
        member.setProfileImageName( profileImageName );

        itemImgRepository.save(member); // ItemImg 엔티티 객체를 저장
    }


    //회원 가입폼의 내용을 데이터 베이스에 저장
    public void saveMember(JoinDto JoinDto, PasswordEncoder passwordEncoder, MultipartFile multipartFile){
        Member member = JoinDto.createEntity(passwordEncoder);
        // 아이디와 이메일 중복여부

        validUserIdEmail( member );
        memberRepository.save(member);

//        //이미지 업로드 및 데이터베이스 저장
//
//        ProfileImg profileImg =new ProfileImg();
//        profileImg.setMember(member);
//        //업로드 및 데이터베이스 저장 위한 itemimgservice 클래스 메서드 호출
//        ProfileImgService.saveMemberImg(profileImg, multipartFile);

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
