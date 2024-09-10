package com.guestbook.Repository;

import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guestbook, Long> {
    //상속은 Guestbook Entity 만들고 나서 할 예정(타입이 안 정해졌으니까)
}
