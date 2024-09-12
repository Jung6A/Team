package com.guestbook.Repository;

import com.guestbook.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 특정 아이디로 멤버 찾기
    Optional<Member> findByUserId(String userId);

    // 특정 이메일로 멤버 찾기
    Optional<Member> findByEmail(String email);

    // 특정 닉네임으로 멤버 찾기
    Optional<Member> findByNickName(String nickName);
}
