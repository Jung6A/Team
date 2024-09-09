package com.guestbook.Repository;

import com.guestbook.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 특정 아이디로 멤버 찾기
    Member findByUserId(String userId);

    // 특정 이메일로 멤버 찾기
    Member findByEmail(String email);

    Member findByNickName(String nickName); // 닉네임으로 검색
}
