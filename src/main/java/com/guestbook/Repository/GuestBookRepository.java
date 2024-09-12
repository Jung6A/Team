package com.guestbook.Repository;

import com.guestbook.Entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestBookRepository extends JpaRepository<Guestbook, Long> {
    Optional<Guestbook> findByGuestbookId(String guestbookId);
}
