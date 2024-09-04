package com.guestbook.Repository;

import com.guestbook.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Member findByUserId(String userId);

    Member findByEmail(String email);
    //상속은 Member Entity 만들고 나서 할 예정(타입이 안 정해졌으니까)
}
