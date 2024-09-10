package com.guestbook.Repository;

import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guestbook, Long> {
    Guestbook findByMemberId(Long id);
}
